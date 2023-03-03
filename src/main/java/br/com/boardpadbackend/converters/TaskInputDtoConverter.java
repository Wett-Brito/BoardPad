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

    @Mappings({
            @Mapping(target = "title",  source = "titleTask"),
            @Mapping(target = "description",  source = "descriptionTask"),
            @Mapping(target = "idStatus",  source = "statusEntity.idStatus"),
            @Mapping(target = "idCategory",  source = "categoryEntity", qualifiedByName = "getLongIdByCategory")
    })
    @Override
    TaskInputDto entityToDto(TaskEntity entity);

    @Named("getCategoryByLongId")
    default CategoryEntity getCategoryByLongId(Long categoryId) {
        if (categoryId == null || categoryId == 0) return null;
        return CategoryEntity.builder().idCategory(categoryId).build();
    }

    @Named("getLongIdByCategory")
    default Long getLongIdByCategory(CategoryEntity category) {
        if (category == null) return 0L;
        return category.getIdCategory();
    }
}
