package br.com.boardpadbackend.service.impl;

import br.com.boardpadbackend.dto.BoardDto;
import br.com.boardpadbackend.entity.BoardEntity;
import br.com.boardpadbackend.exceptions.BadRequestException;
import br.com.boardpadbackend.repositories.BoardRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.mockito.BDDMockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
class BoardServiceImplTest {
    @Mock private BoardRepository boardRepository;
    @InjectMocks private BoardServiceImpl boardService;

    @Test
    public void createBoard_whenDoesntExists () {
        BoardDto expectedBoard = BoardDto.builder().id(1L).codeBoard("TEST-CREATION").build();
        when(boardRepository.findByCodeBoard(ArgumentMatchers.any()))
                .thenReturn(Optional.empty());
        when(boardRepository.save(ArgumentMatchers.any()))
                .thenReturn(BoardEntity.builder()
                        .idBoard(expectedBoard.getId())
                        .codeBoard(expectedBoard.getCodeBoard())
                        .build()
                );
        BoardDto boardServiceResponse = boardService.createBoard("TEST-CREATION");

        assertEquals(expectedBoard, boardServiceResponse);
    }

    @Test
    public void createBoard_whenBoardAlreadyExists () {
        BoardDto expectedBoard = BoardDto.builder().id(1L).codeBoard("TEST-CREATION").build();
        when(boardRepository.findByCodeBoard(ArgumentMatchers.any()))
                .thenReturn(Optional.of(
                        BoardEntity.builder()
                                .idBoard(expectedBoard.getId())
                                .codeBoard(expectedBoard.getCodeBoard())
                                .build()
                ));
        when(boardRepository.save(ArgumentMatchers.any()))
                .thenReturn(null);
        BoardDto boardServiceResponse = boardService.createBoard("TEST-CREATION");

        assertEquals(expectedBoard, boardServiceResponse);
    }

    @Test
    public void createBoard_whenExceptionIsThrown () {
        when(boardRepository.findByCodeBoard(ArgumentMatchers.any()))
                .thenThrow(new RuntimeException("Mocked Expected Exception"));
        BoardDto boardServiceResponse = boardService.createBoard("TEST-CREATION");

        assertNull(boardServiceResponse);
    }

    @Test
    public void findBoardByBoardCode_WhenFound() {
        var expectedBoardEntityReturn = BoardEntity.builder()
                .idBoard(1L)
                .codeBoard("board-test")
                .build();
        when(boardRepository.findByCodeBoard(ArgumentMatchers.eq("board-test")))
                .thenReturn(Optional.of(expectedBoardEntityReturn));

        var boardServiceActualReturn = boardService.findBoardByBoardCode("board-test");

        assertEquals(expectedBoardEntityReturn, boardServiceActualReturn);
    }

    @Test
    public void findBoardByBoardCode_WhenNotFound() {
        when(boardRepository.findByCodeBoard(ArgumentMatchers.eq("board-test")))
                .thenReturn(Optional.empty());

        assertThrows(BadRequestException.class, () -> boardService.findBoardByBoardCode("board-test") );
    }
}