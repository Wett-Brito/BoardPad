package br.com.boardpadbackend.service;

import br.com.boardpadbackend.dto.CategoryDto;

import java.util.List;

public interface CategoryService {
    List<CategoryDto> listAllCategories(String boardCode);
    CategoryDto createCategory (String boardCode, String categoryName);
    void deleteCategoryById(String boardCode, Long idCategory);
}
