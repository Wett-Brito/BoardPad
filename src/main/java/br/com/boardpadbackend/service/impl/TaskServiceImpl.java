package br.com.boardpadbackend.service.impl;

import br.com.boardpadbackend.converters.TaskDtoConverter;
import br.com.boardpadbackend.converters.TaskInputDtoConverter;
import br.com.boardpadbackend.dto.TaskDto;
import br.com.boardpadbackend.dto.inputs.TaskInputDto;
import br.com.boardpadbackend.entity.CategoryEntity;
import br.com.boardpadbackend.entity.StatusEntity;
import br.com.boardpadbackend.entity.TaskEntity;
import br.com.boardpadbackend.entity.projections.TaskProjection;
import br.com.boardpadbackend.repositories.TaskRepository;
import br.com.boardpadbackend.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskServiceImpl implements TaskService {

    private TaskInputDtoConverter taskInputDtoConverter = TaskInputDtoConverter.INSTANCE;
    private TaskDtoConverter taskDtoConverter = TaskDtoConverter.INSTANCE;

    private TaskRepository taskRepository;

    @Autowired
    public TaskServiceImpl(TaskRepository taskRepository) {
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
    public TaskDto createTask(TaskInputDto inputTask) {
        TaskEntity newTask = taskRepository.save(taskInputDtoConverter.dtoToEntity(inputTask));
        return taskDtoConverter.entityToDto(newTask);
    }
}
