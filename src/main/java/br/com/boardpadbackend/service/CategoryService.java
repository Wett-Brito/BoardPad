package br.com.boardpadbackend.service;

import br.com.boardpadbackend.dto.CategoryDto;

import java.util.List;

public interface CategoryService {
    List<CategoryDto> listAllCategories();
    CategoryDto createCategory (String categoryName);
    void deleteCategoryById (Long idCategory);
}
