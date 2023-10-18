package br.com.boardpadbackend.converters;

import br.com.boardpadbackend.dto.inputs.TaskInputDto;
import br.com.boardpadbackend.entity.TaskEntity;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;
import org.springframework.util.StringUtils;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface TaskInputDtoConverter extends Converter <TaskEntity, TaskInputDto> {
    TaskInputDtoConverter INSTANCE = Mappers.getMapper(TaskInputDtoConverter.class);

    @Mappings({
            @Mapping(target = "titleTask", source = "title"),
            @Mapping(target = "descriptionTask", source = "description"),
            @Mapping(target = "statusEntity.idStatus", source = "idStatus")
    })
    @Override
    TaskEntity dtoToEntity(TaskInputDto dto) ;

    @Mappings({
            @Mapping(target = "title",  source = "titleTask"),
            @Mapping(target = "description",  source = "descriptionTask"),
            @Mapping(target = "idStatus",  source = "statusEntity.idStatus")
    })
    @Override
    TaskInputDto entityToDto(TaskEntity entity);

    default void dtoToUpdatableEntity(TaskEntity entity, TaskInputDto dto) {
        if(StringUtils.hasText(dto.getTitle()))
            entity.setTitleTask(dto.getTitle());

        if(StringUtils.hasText(dto.getDescription()))
            entity.setDescriptionTask(dto.getDescription());
    }
}
