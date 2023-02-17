package br.com.boardpadbackend.mockedclasses;

import br.com.boardpadbackend.dto.CategoryDto;
import br.com.boardpadbackend.entity.CategoryEntity;

public class CategoryEntityAndDto {
    public static final CategoryEntity MOCKED_CATEGORY_ENTITY = CategoryEntity.builder()
            .idCategory(1L)
            .nameCategory("Category Test")
            .board(BoardEntityAndDto.BOARD_ENTITY)
            .build();

    public static final CategoryDto MOCKED_CATEGORY_DTO = CategoryDto.builder()
            .id(1L)
            .name("Category Test")
            .build();
}
