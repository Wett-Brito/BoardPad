package br.com.boardpadbackend.converters;

import br.com.boardpadbackend.dto.CategoryDto;
import br.com.boardpadbackend.entity.CategoryEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface CategoryDtoConverter extends Converter<CategoryEntity, CategoryDto> {
    CategoryDtoConverter INSTANCE = Mappers.getMapper(CategoryDtoConverter.class);

    @Mappings({
            @Mapping(target = "idCategory",source = "id"),
            @Mapping(target = "nameCategory",source = "name")
    })
    @Override
    CategoryEntity dtoToEntity(CategoryDto dto);

    @Mappings({
            @Mapping(target = "id",source = "idCategory"),
            @Mapping(target = "name",source = "nameCategory")
    })
    @Override
    CategoryDto entityToDto(CategoryEntity entity);
}
