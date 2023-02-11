package br.com.boardpadbackend.converters;

import br.com.boardpadbackend.dto.TaskDto;
import br.com.boardpadbackend.entity.TaskEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface TaskDtoConverter extends Converter <TaskEntity, TaskDto> {
    TaskDtoConverter INSTANCE = Mappers.getMapper(TaskDtoConverter.class);

    @Mappings({
            @Mapping(target = "idTask", source = "id"),
            @Mapping(target = "titleTask", source = "title"),
            @Mapping(target = "descriptionTask", source = "description"),
            @Mapping(target = "statusEntity.idStatus", source = "idStatus"),
            @Mapping(target = "statusEntity.nameStatus", source = "nameStatus"),
            @Mapping(target = "categoryEntity.idCategory", source = "idCategory"),
            @Mapping(target = "categoryEntity.nameCategory", source = "nameCategory"),
            @Mapping(target = "board.idBoard", source = "idBoard"),
    })
    @Override
    TaskEntity dtoToEntity(TaskDto dto);
    @Mappings({
            @Mapping(target = "id", source = "idTask"),
            @Mapping(target = "title", source = "titleTask"),
            @Mapping(target = "description", source = "descriptionTask"),
            @Mapping(target = "idStatus", source = "statusEntity.idStatus"),
            @Mapping(target = "nameStatus", source = "statusEntity.nameStatus"),
            @Mapping(target = "idCategory", source = "categoryEntity.idCategory"),
            @Mapping(target = "nameCategory", source = "categoryEntity.nameCategory"),
            @Mapping(target = "idBoard", source = "board.idBoard"),
    })
    @Override
    TaskDto entityToDto(TaskEntity entity);
}
