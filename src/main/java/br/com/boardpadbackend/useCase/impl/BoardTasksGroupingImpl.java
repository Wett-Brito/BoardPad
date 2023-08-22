package br.com.boardpadbackend.useCase.impl;

import br.com.boardpadbackend.converters.CategoryDtoConverter;
import br.com.boardpadbackend.converters.TaskDtoConverter;
import br.com.boardpadbackend.dto.BoardDto;
import br.com.boardpadbackend.dto.SynopsisStatus;
import br.com.boardpadbackend.dto.SynopsisTask;
import br.com.boardpadbackend.dto.TaskDto;
import br.com.boardpadbackend.repositories.BoardRepository;
import br.com.boardpadbackend.repositories.CategoryRepository;
import br.com.boardpadbackend.repositories.StatusRepository;
import br.com.boardpadbackend.repositories.TaskRepository;
import br.com.boardpadbackend.service.BoardService;
import br.com.boardpadbackend.useCase.BoardTasksGrouping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.util.List;
import java.util.stream.Collectors;

@Component("BoardTasksGroupingImpl")
public class BoardTasksGroupingImpl implements BoardTasksGrouping {
    private TaskRepository taskRepository;
    private CategoryRepository categoryRepository;
    private StatusRepository statusRepository;

    @Autowired
    public BoardTasksGroupingImpl(TaskRepository taskRepository,
                                  CategoryRepository categoryRepository,
                                  StatusRepository statusRepository) {
        this.taskRepository = taskRepository;
        this.categoryRepository = categoryRepository;
        this.statusRepository = statusRepository;
    }

    @Override
    public BoardDto getBoard(BoardDto boardDto) {
        // Selects and sets all categories present on board
        findAndMapCategories(boardDto);
        findAndGroupTasksIntoStatus(boardDto);
        return boardDto;
    }
    public void findAndMapCategories(BoardDto boardDto){
        boardDto.setCategories(
                categoryRepository.findAllByBoardCode(boardDto.getCodeBoard()).stream()
                        .map(CategoryDtoConverter.INSTANCE::entityToDto)
                        .collect(Collectors.toList())
        );
    }
    public List<SynopsisStatus> findAndGroupTasksIntoStatus(BoardDto boardDto) {
        List<TaskDto> tasksFromBoard = findTasksWithAllData(boardDto.getCodeBoard());
        // Find all status of tasks
        List<SynopsisStatus> synopsisStatusList = filterStatusFromTaskDto(boardDto.getCodeBoard(),tasksFromBoard);
        groupAllSynopsisTasksIntoSynopsisStatus(synopsisStatusList, tasksFromBoard);
        boardDto.setStatus(synopsisStatusList);
        return synopsisStatusList;
    }

    /**
     * Finds boards tasks with their respective status and categories
     * @param boardCode
     * @return
     */
    private List<TaskDto> findTasksWithAllData (String boardCode){
        return taskRepository.findAllWithCategoryAndStatus(boardCode).stream()
                .map(TaskDtoConverter.INSTANCE::entityToDto)
                .collect(Collectors.toList());
    }

    /**
     * Filter all distinct status from a @{@link TaskDto} instance
     * @param tasksFromBoard
     * @return
     */
    private List<SynopsisStatus> filterStatusFromTaskDto(String boardCode, List<TaskDto> tasksFromBoard) {
        var statusList = this.statusRepository.listAllStatusFromBoardCode(boardCode);
        List<SynopsisStatus> synopsisStatusList = statusList.stream()
                .map(item -> SynopsisStatus.builder()
                        .id(BigInteger.valueOf(item.getIdStatus()))
                        .name(item.getNameStatus())
                        .build())
                .collect(Collectors.toList());
        if (tasksFromBoard.stream()
                .filter(item -> item.getIdStatus() == null).count() == 0L)
            synopsisStatusList.add(0, SynopsisStatus.builder()
                    .id(BigInteger.ZERO)
                    .name("Unparented")
                    .build());
        return synopsisStatusList;
    }

    /**
     * Groups all tasks in their respective status
     * @param synopsisStatusList
     * @param tasksFromBoard
     */
    private void groupAllSynopsisTasksIntoSynopsisStatus(
            List<SynopsisStatus> synopsisStatusList,
            List<TaskDto> tasksFromBoard
    ){
        synopsisStatusList.forEach(item -> {
            List<SynopsisTask> synopsisTasks = tasksFromBoard.stream()
                    .filter(task -> item.getId().equals(BigInteger.valueOf(task.getIdStatus())))
                    .map(task -> SynopsisTask.builder()
                            .id(BigInteger.valueOf(task.getId()))
                            .title(task.getTitle())
                            .build())
                    .collect(Collectors.toList());
            item.setTasks(synopsisTasks);
        });
    }

}
