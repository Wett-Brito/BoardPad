package br.com.boardpadbackend.controllers;

import br.com.boardpadbackend.converters.TaskInputDtoConverter;
import br.com.boardpadbackend.dto.inputs.TaskInputDto;
import br.com.boardpadbackend.exceptions.InternalServerErrorException;
import br.com.boardpadbackend.exceptions.NotFoundException;
import br.com.boardpadbackend.mockedclasses.TaskEntityAndDto;
import br.com.boardpadbackend.service.TaskService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.BDDMockito.*;
import static org.mockito.ArgumentMatchers.*;
import static org.junit.jupiter.api.Assertions.*;

@WebMvcTest(TaskController.class)
class TaskControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private TaskService taskService;
    @Autowired
    private ObjectMapper objectMapper;

    /**
     * When tasks are found on the board.
     * @throws Exception
     */
    @Test
    public void listTask_WhenSuccess() throws Exception{
        when(taskService.listAllTasks(eq("board-test"))).thenReturn(List.of(TaskEntityAndDto.MOCK_TASK_DTO));
        mockMvc.perform(get("/tasks")
                .param("board-code", "board-test"))
                .andExpect(status().isOk());
    }

    /**
     * When no tasks are found on the board.
     * @throws Exception
     */
    @Test
    public void listTask_When404() throws Exception{
        when(taskService.listAllTasks(eq("board-test"))).thenThrow(new NotFoundException("No tasks found to board board-test"));
        mockMvc.perform(get("/tasks")
                        .param("board-code", "board-test"))
                .andExpect(status().isNotFound());
    }

    /**
     * When no error occurs in task creation
     * @throws Exception
     */
    @Test
    public void createNewTask_WhenSuccess() throws Exception{
        TaskInputDto taskInputDto = TaskInputDtoConverter.INSTANCE.entityToDto(TaskEntityAndDto.MOCK_TASK_ENTITY);
        when(taskService.createTask(eq("board-test"), eq(taskInputDto)))
                .thenReturn(TaskEntityAndDto.MOCK_TASK_DTO);

        mockMvc.perform(post("/tasks")
                        .queryParam("board-code", "board-test")
                        .content(objectMapper.writeValueAsString(taskInputDto))
                        .contentType("application/json"))
                .andExpect(status().isCreated());
    }

    /**
     * When user tries to create a task on a non-existent board
     * @throws Exception
     */
    @Test
    public void createNewTask_WhenBoardDoesntExists() throws Exception{
        TaskInputDto taskInputDto = TaskInputDtoConverter.INSTANCE.entityToDto(TaskEntityAndDto.MOCK_TASK_ENTITY);
        when(taskService.createTask(eq("board-test"), eq(taskInputDto)))
                .thenThrow(new NotFoundException("The board [board-test] doesn't exists."));

        mockMvc.perform(post("/tasks")
                        .queryParam("board-code", "board-test")
                        .content(objectMapper.writeValueAsString(taskInputDto))
                        .contentType("application/json"))
                .andExpect(status().isNotFound());
    }

    /**
     * When any exception is thrown by Database Connection or any troubles in runtime.
     * @throws Exception
     */
    @Test
    public void createNewTask_WhenInternalError() throws Exception{
        TaskInputDto taskInputDto = TaskInputDtoConverter.INSTANCE.entityToDto(TaskEntityAndDto.MOCK_TASK_ENTITY);
        when(taskService.createTask(eq("board-test"), eq(taskInputDto)))
                .thenThrow(new InternalServerErrorException());

        mockMvc.perform(post("/tasks")
                        .queryParam("board-code", "board-test")
                        .content(objectMapper.writeValueAsString(taskInputDto))
                        .contentType("application/json"))
                .andExpect(status().isInternalServerError());
    }

    /**
     * When no errors occur while updating task status
     * @throws Exception
     */
    @Test
    public void updateTask_WhenSuccess() throws Exception{
        doNothing().when(taskService).updateStatusTask(eq("board-test"), eq(1L),eq(2L));

        mockMvc.perform(put("/tasks/1/status")
                        .queryParam("board-code", "board-test")
                        .queryParam("new-status-id", "2")
                        .contentType("application/json"))
                .andExpect(status().isOk());
    }

    /**
     * When the chosen task or status is not found.
     * @throws Exception
     */
    @Test
    public void updateTask_WhenTaskOrStatusNotFound() throws Exception{
        doThrow(NotFoundException.class)
                .when(taskService).updateStatusTask(eq("board-test"), eq(1L),eq(2L));

        mockMvc.perform(put("/tasks/1/status")
                        .queryParam("board-code", "board-test")
                        .queryParam("new-status-id", "2")
                        .contentType("application/json"))
                .andExpect(status().isNotFound());
    }

    /**
     * When service throw generic internal server error
     * @throws Exception
     */
    @Test
    public void updateTask_WhenGenericInternalServerError() throws Exception{
        doThrow(InternalServerErrorException.class)
                .when(taskService).updateStatusTask(eq("board-test"), eq(1L),eq(2L));

        mockMvc.perform(put("/tasks/1/status")
                        .queryParam("board-code", "board-test")
                        .queryParam("new-status-id", "2")
                        .contentType("application/json"))
                .andExpect(status().isInternalServerError());
    }

    /**
     * When service can delete task successfully
     * @throws Exception
     */
    @Test
    public void deleteTask_WhenSuccess() throws Exception{
        doNothing()
                .when(taskService).deleteTask(eq("board-test"), eq(1L));

        mockMvc.perform(delete("/tasks/1")
                        .queryParam("board-code", "board-test")
                        .contentType("application/json"))
                .andExpect(status().isNoContent());
    }

    /**
     * When service can't find and delete a task because it not exists
     * @throws Exception
     */
    @Test
    public void deleteTask_WhenTaskNotFound() throws Exception{
        doThrow(NotFoundException.class)
                .when(taskService).deleteTask(eq("board-test"), eq(1L));

        mockMvc.perform(delete("/tasks/1")
                        .queryParam("board-code", "board-test")
                        .contentType("application/json"))
                .andExpect(status().isNotFound());
    }

    /**
     * When service throws a generic exception.
     * @throws Exception
     */
    @Test
    public void deleteTask_WhenInternalServerError() throws Exception{
        doThrow(InternalServerErrorException.class)
                .when(taskService).deleteTask(eq("board-test"), eq(1L));

        mockMvc.perform(delete("/tasks/1")
                        .queryParam("board-code", "board-test")
                        .contentType("application/json"))
                .andExpect(status().isInternalServerError());
    }
}