package br.com.boardpadbackend.service;

import br.com.boardpadbackend.dto.SynopsisStatus;
import br.com.boardpadbackend.dto.TaskDto;
import br.com.boardpadbackend.dto.inputs.TaskInputDto;

import java.math.BigInteger;
import java.util.List;

public interface TaskService {
    List<SynopsisStatus> listAllTasks(String boardCode);
    void updateStatusTask (String boardCode, Long idTask, Long newStatusId);
    TaskDto createTask(String boardCode, TaskInputDto inputTask);
    void deleteTask(String boardCode, Long idTask);
    TaskDto getTaskById (BigInteger taskId);
    void updateTaskById(BigInteger taskId, TaskInputDto task);
}
