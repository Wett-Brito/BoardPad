package br.com.boardpadbackend.mockedclasses;

import br.com.boardpadbackend.dto.StatusDto;
import br.com.boardpadbackend.entity.StatusEntity;

public class StatusEntityAndDto {
    public static final StatusDto MOCKED_STATUS_DTO = StatusDto.builder()
            .id(1L)
            .name("TO DO")
            .build();

    public static final StatusEntity MOCKED_STATUS_ENTITY = StatusEntity.builder()
            .idStatus(1L)
            .nameStatus("TO DO")
            .board(BoardEntityAndDto.BOARD_ENTITY)
            .build();
}
