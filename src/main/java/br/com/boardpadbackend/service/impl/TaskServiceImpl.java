package br.com.boardpadbackend.service.impl;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import br.com.boardpadbackend.service.BoardService;
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

@Service
public class TaskServiceImpl implements TaskService {

    private TaskInputDtoConverter taskInputDtoConverter = TaskInputDtoConverter.INSTANCE;
    private TaskDtoConverter taskDtoConverter = TaskDtoConverter.INSTANCE;

    private BoardService boardService;
    private TaskRepository taskRepository;

    @Autowired
    public TaskServiceImpl(BoardService boardService, TaskRepository taskRepository) {
        this.boardService = boardService;
        this.taskRepository = taskRepository;
    }

    @Override
    public List<TaskDto> listAllTasks() {
        return taskRepository.findAllWithCategoryAndStatus().stream()
                .map(taskDtoConverter::entityToDto)
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public void updateStatusTask(Long idTask, Long newStatusId) {
        taskRepository.updateTaskStatus(idTask, newStatusId);
    }

    @Transactional
    @Override
    public TaskDto createTask(String boardCode, TaskInputDto inputTask) {
        try {
            var foundBoard = boardService.findBoardByBoardCode(boardCode);
            TaskEntity newTask = taskInputDtoConverter.dtoToEntity(inputTask);
            newTask.setBoard(foundBoard);

            taskRepository.save(newTask);
            return taskDtoConverter.entityToDto(newTask);
        }
        catch(Exception ex) {
            if(ex instanceof SQLIntegrityConstraintViolationException){
                throw new RuntimeException("Erro ao encontrar");
            }
            return null;
        }

    }
    
    @Transactional
    @Override
    public void deleteTask(Long taskId) {
    	try {
    		taskRepository.deleteById(taskId);
    	}catch (EmptyResultDataAccessException e) {
    		throw new RuntimeException("Erro ao encontrar ID da task para deletar");
    	}
    }
}
