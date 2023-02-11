package br.com.boardpadbackend.controllers;

import br.com.boardpadbackend.dto.TaskDto;
import br.com.boardpadbackend.dto.inputs.TaskInputDto;
import br.com.boardpadbackend.service.TaskService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
            @ApiResponse(code = 500, message = "Internal server error. Task wasn't created")
    })
    @PostMapping
    public ResponseEntity<TaskDto> createNewTask (@RequestBody TaskInputDto inputTask,
                                                  @RequestParam("board-code") String boardCode){
        return ResponseEntity.ok().body(taskService.createTask(boardCode, inputTask));
    }

    @ApiOperation("List all tasks")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 204, message = "No content to show"),
            @ApiResponse(code = 500, message = "Internal server error. Task wasn't created")
    })
    @GetMapping
    public List<TaskDto> list (@RequestParam("board-code") String boardCode){
        return taskService.listAllTasks(boardCode);
    }

    @ApiOperation("Update tasks")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 500, message = "Internal server error. Task wasn't created")
    })
    @PutMapping(path = "{id}")
    public void updateTask (@PathVariable("id") Long taskId, @RequestParam("newStatusId") Long newStatusId) {
        taskService.updateStatusTask(taskId, newStatusId);
    }
    
    @ApiOperation("Delete tasks")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Task deleted successfully"),
            @ApiResponse(code = 500, message = "Internal server error. Task wasn't deleted")
    })
    @DeleteMapping(path = "{id}")
    public ResponseEntity<Void> deleteTask (@PathVariable("id") Long taskId) {
        taskService.deleteTask(taskId);
        
        return ResponseEntity.noContent().build();
    }
    
    
    
    
    
}
