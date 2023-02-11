package br.com.boardpadbackend.service.impl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import br.com.boardpadbackend.entity.StatusEntity;
import br.com.boardpadbackend.exceptions.InternalServerErrorException;
import br.com.boardpadbackend.exceptions.NotFoundException;
import br.com.boardpadbackend.service.BoardService;
import br.com.boardpadbackend.service.StatusService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import br.com.boardpadbackend.converters.TaskDtoConverter;
import br.com.boardpadbackend.converters.TaskInputDtoConverter;
import br.com.boardpadbackend.dto.TaskDto;
import br.com.boardpadbackend.dto.inputs.TaskInputDto;
import br.com.boardpadbackend.entity.TaskEntity;
import br.com.boardpadbackend.repositories.TaskRepository;
import br.com.boardpadbackend.service.TaskService;

@Log4j2
@Service
public class TaskServiceImpl implements TaskService {

    private TaskInputDtoConverter taskInputDtoConverter = TaskInputDtoConverter.INSTANCE;
    private TaskDtoConverter taskDtoConverter = TaskDtoConverter.INSTANCE;

    private BoardService boardService;
    private TaskRepository taskRepository;
    private StatusService statusService;

    @Autowired
    public TaskServiceImpl(BoardService boardService,
                           TaskRepository taskRepository,
                           StatusService statusService) {
        this.boardService = boardService;
        this.taskRepository = taskRepository;
        this.statusService = statusService;
    }

    @Override
    public List<TaskDto> listAllTasks(String boardCode) {
        var taskList = taskRepository.findAllWithCategoryAndStatus(boardCode);
        if(taskList.isEmpty()) throw new NotFoundException("No tasks found to board" + boardCode);
        return taskList.stream()
                .map(taskDtoConverter::entityToDto)
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public void updateStatusTask(String boardCode, Long idTask, Long newStatusId) {
        TaskEntity task = getTaskByBoardCodeAndTaskId(boardCode, idTask);
        StatusEntity statusFound = statusService.getStatusEntityByBoardCodeAndStatusId(boardCode, newStatusId);
        task.setStatusEntity(statusFound);
    }

    @Transactional
    @Override
    public TaskDto createTask(String boardCode, TaskInputDto inputTask) {
        try {
            var foundBoard = boardService.findBoardByBoardCode(boardCode);
            TaskEntity newTask = taskInputDtoConverter.dtoToEntity(inputTask);
            newTask.setBoard(foundBoard);
            newTask.setDateCreationTask(new Date());

            taskRepository.save(newTask);
            return taskDtoConverter.entityToDto(newTask);
        }
        catch(Exception ex) {
            log.error("Error while trying create new TASK ENTITY " + inputTask, ex);
            throw new InternalServerErrorException();
        }

    }
    
    @Transactional
    @Override
    public void deleteTask(String boardCode, Long taskId) {
        try {
            var taskToDelete = getTaskByBoardCodeAndTaskId(boardCode, taskId);
            taskRepository.delete(taskToDelete);
        }
        catch (Exception ex){
            log.error(ex);
            if(!(ex instanceof NotFoundException))
                throw new InternalServerErrorException();
            else throw new NotFoundException(ex.getMessage());
        }
    }

    public TaskEntity getTaskByBoardCodeAndTaskId(String boardCode, Long taskId) {
        var taskFound = taskRepository.getTaskByBoardCodeAndIdTask(boardCode, taskId);
        if (taskFound.isEmpty()) throw new NotFoundException("Task ["
                + taskId
                + "] not found on board ["
                + boardCode
                + "].");
        return taskFound.get();
    }
}
