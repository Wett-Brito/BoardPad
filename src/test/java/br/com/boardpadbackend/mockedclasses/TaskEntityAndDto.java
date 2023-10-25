package br.com.boardpadbackend.mockedclasses;

import br.com.boardpadbackend.converters.TaskDtoConverter;
import br.com.boardpadbackend.dto.TaskDto;
import br.com.boardpadbackend.entity.TaskEntity;

import java.sql.Date;

public class TaskEntityAndDto {
    private final static TaskDtoConverter taskDtoConverter = TaskDtoConverter.INSTANCE;

    public final static TaskEntity MOCK_TASK_ENTITY = TaskEntity.builder()
            .idTask(1L)
            .titleTask("Lorem Ipsum")
            .descriptionTask("Is simply dummy text of the printing and typesetting industry")
            .dateCreationTask(Date.valueOf("2023-02-18"))
            .board(BoardEntityAndDto.BOARD_ENTITY)
            .statusEntity(StatusEntityAndDto.MOCKED_STATUS_ENTITY)
            .build();

    public final static TaskDto MOCK_TASK_DTO = taskDtoConverter.entityToDto(MOCK_TASK_ENTITY);
}
