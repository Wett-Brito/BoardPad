package br.com.boardpadbackend.controllers;

import br.com.boardpadbackend.dto.GenericResponseDTO;
import br.com.boardpadbackend.dto.SynopsisStatus;
import br.com.boardpadbackend.dto.TaskDto;
import br.com.boardpadbackend.dto.inputs.TaskInputDto;
import br.com.boardpadbackend.service.TaskService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.models.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.util.List;

@RequestMapping(path = "/tasks")
@RestController
@Api(value = "Tasks controller", tags = {"Tasks"})
public class TaskController {
    private TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @ApiOperation("Create new Tasks")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Task created successfully"),
            @ApiResponse(code = 400, message = "Board doesn't exists"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    @PostMapping
    public ResponseEntity<TaskDto> createNewTask (@RequestBody TaskInputDto inputTask,
                                                  @RequestParam("board-code") String boardCode){
        return ResponseEntity.status(HttpStatus.CREATED).body(taskService.createTask(boardCode, inputTask));
    }

    @ApiOperation("List all tasks of specific board")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 404, message = "No tasks found on board"),
            @ApiResponse(code = 500, message = "Internal server error. Task wasn't created")
    })
    @GetMapping
    public List<SynopsisStatus> list (@RequestParam("board-code") String boardCode){
        return taskService.listAllTasks(boardCode);
    }

    @ApiOperation("Update task status")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 404, message = "Task/status don't found in board"),
            @ApiResponse(code = 500, message = "Internal server error. Task wasn't updated")
    })
    @PutMapping(path = "{task-id}/status")
    public void updateTask (@RequestParam("board-code") String boardCode,
                            @PathVariable("task-id") Long taskId,
                            @RequestParam("new-status-id") Long newStatusId) {
        taskService.updateStatusTask(boardCode, taskId, newStatusId);
    }
    
    @ApiOperation("Delete tasks")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Task deleted successfully"),
            @ApiResponse(code = 404, message = "Task not found"),
            @ApiResponse(code = 500, message = "Internal server error. Task wasn't deleted")
    })
    @DeleteMapping(path = "{id}")
    public ResponseEntity<Void> deleteTask (@PathVariable("id") Long taskId,
                                            @RequestParam("board-code") String boardCode
    ) {
        taskService.deleteTask(boardCode, taskId);
        
        return ResponseEntity.noContent().build();
    }

    @ApiOperation("Get Task By Id")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Task found"),
            @ApiResponse(code = 404, message = "Task not found"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    @GetMapping(path = "{task-id}")
    public ResponseEntity<GenericResponseDTO<TaskDto>> getTaskById (@PathVariable("task-id") BigInteger taskId){
        TaskDto taskFound = taskService.getTaskById(taskId);
        return ResponseEntity.ok(GenericResponseDTO
                .<TaskDto>builder()
                .status("Task found")
                .response(taskFound)
                .build());
    }
    @ApiOperation("Update Task By Id")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Task updated with success"),
            @ApiResponse(code = 404, message = "Task not found"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    @PutMapping(path = "{task-id}")
    public ResponseEntity<GenericResponseDTO<?>> updateTaskById(
            @PathVariable("task-id") BigInteger taskId,
            @RequestBody TaskInputDto inputDto
    ) {
        taskService.updateTaskById(taskId, inputDto);
        return ResponseEntity.ok(GenericResponseDTO.builder()
                .status("200 OK")
                .message("Task updated successfully.")
                .build());
    }
}