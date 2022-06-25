package br.com.boardpadbackend.converters;

import br.com.boardpadbackend.dto.inputs.TaskInputDto;
import br.com.boardpadbackend.entity.TaskEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface TaskInputDtoConverter extends Converter <TaskEntity, TaskInputDto> {
    TaskInputDtoConverter INSTANCE = Mappers.getMapper(TaskInputDtoConverter.class);

    @Mappings({
            @Mapping(target = "titleTask", source = "title"),
            @Mapping(target = "descriptionTask", source = "description"),
            @Mapping(target = "statusEntity.idStatus", source = "idStatus"),
            @Mapping(target = "categoryEntity.idCategory", source = "idCategory"),
            @Mapping(target = "boardEntity.idBoard", source = "idBoard")
    })
    @Override
    TaskEntity dtoToEntity(TaskInputDto dto) ;
}
