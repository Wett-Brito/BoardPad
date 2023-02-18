package br.com.boardpadbackend.service.impl;

import br.com.boardpadbackend.exceptions.NotFoundException;
import br.com.boardpadbackend.mockedclasses.BoardEntityAndDto;
import br.com.boardpadbackend.mockedclasses.StatusEntityAndDto;
import br.com.boardpadbackend.repositories.StatusRepository;
import br.com.boardpadbackend.repositories.TaskRepository;
import br.com.boardpadbackend.service.BoardService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
class StatusServiceImplTest {
    @Mock private StatusRepository statusRepository;
    @Mock private TaskRepository taskRepository;
    @Mock private BoardService boardService;
    @InjectMocks private StatusServiceImpl statusService;

    @Test
    public void listAllStatus_whenFound() {
        when(statusRepository.listAllStatusFromBoardCode(eq("board-test")))
                .thenReturn(List.of(StatusEntityAndDto.MOCKED_STATUS_ENTITY));

        final var EXPECTED_STATUS = List.of(StatusEntityAndDto.MOCKED_STATUS_DTO);
        final var ACTUAL_STATUS = statusService.listAllStatus("board-test");
        assertEquals(EXPECTED_STATUS, ACTUAL_STATUS);
    }

    @Test
    public void listAllStatus_whenNotFound() {
        when(statusRepository.listAllStatusFromBoardCode(eq("board-test")))
                .thenReturn(new ArrayList<>());
        assertThrows(NotFoundException.class, ()->statusService.listAllStatus("board-test"));
    }

    @Test
    public void getStatusEntityByBoardCodeAndStatusId_whenFound () {
        when(statusRepository.getStatusByIdAndBoardCode(eq(1L), eq("board-test")))
                .thenReturn(Optional.of(StatusEntityAndDto.MOCKED_STATUS_ENTITY));

        final var EXPECTED_STATUS = StatusEntityAndDto.MOCKED_STATUS_ENTITY;
        final var ACTUAL_STATUS = statusService
                .getStatusEntityByBoardCodeAndStatusId("board-test", 1L);
        assertEquals(EXPECTED_STATUS, ACTUAL_STATUS);
    }

    @Test
    public void getStatusEntityByBoardCodeAndStatusId_whenNotFound () {
        when(statusRepository.getStatusByIdAndBoardCode(eq(1L), eq("board-test")))
                .thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, ()-> statusService
                .getStatusEntityByBoardCodeAndStatusId("board-test", 1L));
    }
    @Test
    public void updateStatusName_whenSuccess() {
        when(statusRepository.getStatusByIdAndBoardCode(eq(1L), eq("board-test")))
                .thenReturn(Optional.of(StatusEntityAndDto.MOCKED_STATUS_ENTITY));

        assertDoesNotThrow(()->statusService.updateStatusName(1L, "TO DO", "board-test"));
    }

    @Test
    public void updateStatusName_whenError () {
        when(statusRepository.getStatusByIdAndBoardCode(eq(1L), eq("board-test")))
                .thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, ()-> statusService
                .updateStatusName(1L, "TO DO", "board-test"));
    }

    @Test
    public void createNewStatus_whenSuccess() {
        when(boardService.findBoardByBoardCode(eq("board-test"))).thenReturn(BoardEntityAndDto.BOARD_ENTITY);
        when(statusRepository.save(any())).thenReturn(StatusEntityAndDto.MOCKED_STATUS_ENTITY);

        final var EXPECTED_STATUS = StatusEntityAndDto.MOCKED_STATUS_DTO;
        final var ACTUAL_STATUS = statusService.createNewStatus("board-test", "TO DO");

        assertEquals(EXPECTED_STATUS,ACTUAL_STATUS);
    }

    @Test
    public void createNewStatus_whenBoardError() {
        when(boardService.findBoardByBoardCode(eq("board-test")))
                .thenThrow(new NotFoundException("The board [board-test] doesn't exists."));

        assertThrows(NotFoundException.class,
                ()-> statusService.createNewStatus("board-test", "new-status"));
    }

    @Test
    public void deleteStatus_whenSuccess() {
        when(statusRepository.getStatusByIdAndBoardCode(eq(1L), eq("board-test")))
                .thenReturn(Optional.of(StatusEntityAndDto.MOCKED_STATUS_ENTITY));
        doNothing().when(taskRepository).setAllStatusNullOfTasksThatBelongsToStatusById(any());
        doNothing().when(statusRepository).delete(any());
        assertDoesNotThrow(()->statusService.deleteStatus("board-test", 1L));
    }

    @Test
    public void deleteStatus_whenError() {
        when(statusRepository.getStatusByIdAndBoardCode(eq(1L), eq("board-test")))
                .thenReturn(Optional.empty());
        assertThrows(NotFoundException.class,()->statusService.deleteStatus("board-test", 1L));
    }
}