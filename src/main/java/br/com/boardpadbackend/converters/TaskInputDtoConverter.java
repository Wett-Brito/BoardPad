package br.com.boardpadbackend.converters;

import br.com.boardpadbackend.dto.inputs.TaskInputDto;
import br.com.boardpadbackend.entity.CategoryEntity;
import br.com.boardpadbackend.entity.TaskEntity;
import jdk.jfr.Name;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface TaskInputDtoConverter extends Converter <TaskEntity, TaskInputDto> {
    TaskInputDtoConverter INSTANCE = Mappers.getMapper(TaskInputDtoConverter.class);

    @Mappings({
            @Mapping(target = "titleTask", source = "title"),
            @Mapping(target = "descriptionTask", source = "description"),
            @Mapping(target = "statusEntity.idStatus", source = "idStatus"),
            @Mapping(target = "categoryEntity", source = "idCategory", qualifiedByName = "getCategoryByLongId")
    })
    @Override
    TaskEntity dtoToEntity(TaskInputDto dto) ;

    @Named("getCategoryByLongId")
    default CategoryEntity getCategoryByLongId(Long categoryId) {
        if (categoryId == null || categoryId == 0) return null;
        return CategoryEntity.builder().idCategory(categoryId).build();
    }
}
