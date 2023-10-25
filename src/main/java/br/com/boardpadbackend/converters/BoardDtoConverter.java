package br.com.boardpadbackend.converters;

import br.com.boardpadbackend.dto.BoardDto;
import br.com.boardpadbackend.entity.BoardEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface BoardDtoConverter extends Converter <BoardEntity, BoardDto> {
    BoardDtoConverter INSTANCE = Mappers.getMapper(BoardDtoConverter.class);
    @Mappings({
            @Mapping(target = "idBoard", source = "id"),
            @Mapping(target = "codeBoard", source = "codeBoard")
    })
    @Override
    BoardEntity dtoToEntity(BoardDto dto);

    @Mappings({
            @Mapping(target = "id", source = "idBoard"),
            @Mapping(target = "codeBoard", source = "codeBoard")
    })
    @Override
    BoardDto entityToDto(BoardEntity entity);
}
