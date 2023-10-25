package br.com.boardpadbackend.service.impl;

import br.com.boardpadbackend.converters.TaskDtoConverter;
import br.com.boardpadbackend.converters.TaskInputDtoConverter;
import br.com.boardpadbackend.dto.SynopsisStatus;
import br.com.boardpadbackend.dto.inputs.TaskInputDto;
import br.com.boardpadbackend.exceptions.BadRequestException;
import br.com.boardpadbackend.exceptions.InternalServerErrorException;
import br.com.boardpadbackend.exceptions.NotFoundException;
import br.com.boardpadbackend.mockedclasses.BoardEntityAndDto;
import br.com.boardpadbackend.mockedclasses.StatusEntityAndDto;
import br.com.boardpadbackend.mockedclasses.TaskEntityAndDto;
import br.com.boardpadbackend.repositories.TaskRepository;
import br.com.boardpadbackend.service.BoardService;
import br.com.boardpadbackend.service.StatusService;
import br.com.boardpadbackend.useCase.BoardTasksGrouping;
import org.hibernate.annotations.NotFound;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class TaskServiceImplTest {
    private TaskInputDtoConverter taskInputDtoConverter = TaskInputDtoConverter.INSTANCE;
    private TaskDtoConverter taskDtoConverter = TaskDtoConverter.INSTANCE;

    @Mock private BoardService boardService;
    @Mock private TaskRepository taskRepository;
    @Mock private StatusService statusService;
    @Mock private BoardTasksGrouping boardTasksGrouping;

    @InjectMocks private TaskServiceImpl taskService;

    @Test
    public void listAllTasks_whenSucces() {
        when(boardTasksGrouping.findAndGroupTasksIntoStatus((any())))
                .thenReturn(List.of(SynopsisStatus.builder().build()));
        final Integer EXPECTED_RESPONSE = 1;
        final var ACTUAL_RESPONSE = taskService.listAllTasks("board-test");

        assertEquals(EXPECTED_RESPONSE, ACTUAL_RESPONSE.size());
    }

    @Test
    public void listAllTasks_whenNotFound() {
        when(boardTasksGrouping.findAndGroupTasksIntoStatus((any())))
                .thenReturn(new ArrayList<>());

        assertThrows(NotFoundException.class, () -> taskService.listAllTasks("board-test"));
    }

    @Test
    public void getTaskByBoardCodeAndIdTask_whenSuccess() {
        when(taskRepository.getTaskByBoardCodeAndIdTask("board-test", 1L))
                .thenReturn(Optional.of(TaskEntityAndDto.MOCK_TASK_ENTITY));

        final var EXPECTED_RESPONSE = TaskEntityAndDto.MOCK_TASK_ENTITY;
        final var ACTUAL_RESPONSE = taskService.getTaskByBoardCodeAndTaskId("board-test", 1L);

        assertEquals(EXPECTED_RESPONSE, ACTUAL_RESPONSE);
    }
    @Test
    public void getTaskByBoardCodeAndIdTask_whenNotFound() {
        when(taskRepository.getTaskByBoardCodeAndIdTask("board-test", 1L))
                .thenReturn(Optional.empty());

        assertThrows(NotFoundException.class,
                () -> taskService.getTaskByBoardCodeAndTaskId("board-test", 1L));
    }

    @Test
    public void updateStatusTask_whenSuccess() {
        when(statusService.getStatusEntityByBoardCodeAndStatusId("board-test", 1L))
                .thenReturn(StatusEntityAndDto.MOCKED_STATUS_ENTITY);
        when(taskRepository.getTaskByBoardCodeAndIdTask("board-test", 1L))
                .thenReturn(Optional.of(TaskEntityAndDto.MOCK_TASK_ENTITY));

        assertDoesNotThrow(()-> taskService.updateStatusTask("board-test", 1L, 1L));
    }

    @Test
    public void createTask_whenSuccess() {
        when(boardService.findBoardByBoardCode("board-test"))
                .thenReturn(BoardEntityAndDto.BOARD_DTO);
        when(taskRepository.save(any())).thenReturn(TaskEntityAndDto.MOCK_TASK_ENTITY);

        final var EXPECTED_RESPONSE = TaskEntityAndDto.MOCK_TASK_DTO;
        final var ACTUAL_RESPONSE = taskService.createTask("board-test", TaskInputDto.builder()
                .idStatus(1L)
                .idStatus(1L)
                .title(EXPECTED_RESPONSE.getTitle())
                .description(EXPECTED_RESPONSE.getDescription())
                .build());

        assertEquals(EXPECTED_RESPONSE, ACTUAL_RESPONSE);
    }

    @Test
    public void createTask_whenBoardNotFound() {
        when(boardService.findBoardByBoardCode("board-test"))
                .thenThrow(new NotFoundException("The board [board-test] doesn't exists."));

        assertThrows(NotFoundException.class, ()-> taskService.createTask("board-test", TaskInputDto.builder()
                .idStatus(1L)
                .idStatus(1L)
                .title(TaskEntityAndDto.MOCK_TASK_DTO.getTitle())
                .description(TaskEntityAndDto.MOCK_TASK_DTO.getDescription())
                .build())
        );

    }

    @Test
    public void createTask_whenInternalServerException() {
        when(boardService.findBoardByBoardCode(eq("board-test")))
                .thenReturn(BoardEntityAndDto.BOARD_DTO);
        when(taskRepository.save(any())).thenThrow(new RuntimeException("MOCKED GENERIC ERROR"));

        assertThrows(InternalServerErrorException.class, ()-> taskService.createTask("board-test", TaskInputDto.builder()
                .idStatus(1L)
                .idStatus(1L)
                .title(TaskEntityAndDto.MOCK_TASK_DTO.getTitle())
                .description(TaskEntityAndDto.MOCK_TASK_DTO.getDescription())
                .build())
        );

    }

    @Test
    public void deleteTask_whenSuccess() {
        when(taskRepository.getTaskByBoardCodeAndIdTask("board-test", 1L))
                .thenReturn(Optional.of(TaskEntityAndDto.MOCK_TASK_ENTITY));
        doNothing().when(taskRepository).delete(any());

        assertDoesNotThrow(()-> taskService.deleteTask("board-test", 1L));
    }

    @Test
    public void deleteTask_whenNotFoundTask() {
        when(taskRepository.getTaskByBoardCodeAndIdTask("board-test", 1L))
                .thenReturn(Optional.empty());
        doNothing().when(taskRepository).delete(any());

        assertThrows(NotFoundException.class, ()-> taskService.deleteTask("board-test", 1L));
    }

    @Test
    public void deleteTask_whenInternalServerError() {
        when(taskRepository.getTaskByBoardCodeAndIdTask("board-test", 1L))
                .thenReturn(Optional.of(TaskEntityAndDto.MOCK_TASK_ENTITY));
        doThrow(new RuntimeException("generic-exception")).when(taskRepository).delete(any());

        assertThrows(InternalServerErrorException.class, () -> taskService.deleteTask("board-test", 1L));
    }

    @Test
    public void getTaskByIdWhenFound() {
        when(taskRepository.findById(1L)).thenReturn(Optional.of(TaskEntityAndDto.MOCK_TASK_ENTITY));
        final var EXPECTED_RESPONSE = TaskEntityAndDto.MOCK_TASK_DTO;
        final var ACTUAL_RESPONSE = taskService.getTaskById(BigInteger.ONE);
        assertEquals(EXPECTED_RESPONSE, ACTUAL_RESPONSE);
    }

    @Test
    public void getTaskByIdWhenNotFound() {
        when(taskRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, ()-> taskService.getTaskById(BigInteger.ONE));
    }

    @Test
    public void updateTaskDataWhenSuccess() {
        when(taskRepository.findById(any())).thenReturn(Optional.of(TaskEntityAndDto.MOCK_TASK_ENTITY));
        when(taskRepository.save(any())).thenReturn(TaskEntityAndDto.MOCK_TASK_ENTITY);
        assertDoesNotThrow(()-> taskService.updateTaskById(BigInteger.ONE, new TaskInputDto()));
    }

    @Test
    public void updateTaskDataWhenTaskDoesntExists() {
        when(taskRepository.findById(any())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class,()-> taskService.updateTaskById(BigInteger.ONE, new TaskInputDto()));
    }
}