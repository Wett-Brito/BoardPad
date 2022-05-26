package br.com.boardpadbackend.service;

import br.com.boardpadbackend.dto.TaskDto;
import br.com.boardpadbackend.dto.inputs.TaskInputDto;

import java.util.List;

public interface TaskService {
    List<TaskDto> listAllTasks();
    void updateStatusTask (Long idTask, Long newStatusId);
    TaskDto createTask(TaskInputDto inputTask);
}
