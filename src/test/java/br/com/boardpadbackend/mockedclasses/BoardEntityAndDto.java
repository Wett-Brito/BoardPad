package br.com.boardpadbackend.mockedclasses;

import br.com.boardpadbackend.dto.BoardDto;
import br.com.boardpadbackend.entity.BoardEntity;

public class BoardEntityAndDto {
    public static final BoardEntity BOARD_ENTITY = BoardEntity.builder()
            .idBoard(1L)
            .codeBoard("test-board")
            .build();
    public static final BoardDto BOARD_DTO = BoardDto.builder()
            .id(1L)
            .codeBoard("test-board")
            .build();
}
