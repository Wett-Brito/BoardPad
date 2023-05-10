package br.com.boardpadbackend.service.impl;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import br.com.boardpadbackend.converters.BoardDtoConverter;
import br.com.boardpadbackend.converters.CategoryDtoConverter;
import br.com.boardpadbackend.converters.TaskDtoConverter;
import br.com.boardpadbackend.dto.*;
import br.com.boardpadbackend.entity.TaskEntity;
import br.com.boardpadbackend.exceptions.NotFoundException;
import br.com.boardpadbackend.repositories.CategoryRepository;
import br.com.boardpadbackend.repositories.TaskRepository;
import br.com.boardpadbackend.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.boardpadbackend.entity.BoardEntity;
import br.com.boardpadbackend.repositories.BoardRepository;
import br.com.boardpadbackend.service.BoardService;

import static java.util.stream.Collectors.groupingBy;

@Service
public class BoardServiceImpl implements BoardService {
    private BoardRepository boardRepository;
    private TaskRepository taskRepository;
    private CategoryRepository categoryRepository;

    @Autowired
    public BoardServiceImpl(BoardRepository boardRepository,
                            TaskRepository taskRepository,
                            CategoryRepository categoryRepository) {
        this.boardRepository = boardRepository;
        this.taskRepository = taskRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public BoardDto createBoard(String boardCode) {
        try {
            Optional<BoardEntity> existentBoard = boardRepository.findByCodeBoard(boardCode);
            if (existentBoard.isPresent()) {
                return BoardDto.builder()
                        .id(existentBoard.get().getIdBoard())
                        .codeBoard(existentBoard.get().getCodeBoard())
                        .build();
            }
            BoardEntity newBoardEntity = boardRepository.save(BoardEntity.builder()
                    .codeBoard(boardCode)
                    .build());

            return BoardDto.builder()
                    .id(newBoardEntity.getIdBoard())
                    .codeBoard(newBoardEntity.getCodeBoard())
                    .build();

        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public BoardDto findBoardByBoardCode(String boardCode) {
        var foundBoard = boardRepository.findByCodeBoard(boardCode);
        if (foundBoard.isEmpty()) throw new NotFoundException("The board [" + boardCode + "] doesn't exists.");
        return BoardDtoConverter.INSTANCE.entityToDto(foundBoard.get());
    }

    @Override
    public BoardDto getBoardWithAllDataByBoardCode(String boardCode) {
        var foundBoard = this.findBoardByBoardCode(boardCode);
        // Selects and sets all categories present on board
        foundBoard.setCategories(
                categoryRepository.findAllByBoardCode(boardCode).stream()
                        .map(CategoryDtoConverter.INSTANCE::entityToDto)
                        .collect(Collectors.toList())
        );

        // Select all tasks present on board
        List<TaskDto> tasksFromBoard =
                taskRepository.findAllWithCategoryAndStatus(boardCode).stream()
                        .map(TaskDtoConverter.INSTANCE::entityToDto)
                        .collect(Collectors.toList());


        // Group all tasks from board
        var groupedTasks = tasksFromBoard.stream()
                .collect(groupingBy(item -> new SynopsisStatus(BigInteger.valueOf(item.getIdStatus()),item.getNameStatus()),
                                groupingBy(item -> new SynopsisTask(BigInteger.valueOf(item.getId()), item.getTitle())))
                );

        List<SynopsisStatus> status = new ArrayList<>(groupedTasks.keySet());
        status.forEach(item -> {
            item.setTasks(new ArrayList<>(groupedTasks.get(item).keySet()));
        });

        foundBoard.setStatus(status);
        return foundBoard;
    }

}
