package br.com.boardpadbackend.service.impl;

import br.com.boardpadbackend.dto.CategoryDto;
import br.com.boardpadbackend.entity.CategoryEntity;
import br.com.boardpadbackend.repositories.CategoryRepository;
import br.com.boardpadbackend.repositories.TaskRepository;
import br.com.boardpadbackend.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

@Service
public class CategoryServiceImpl implements CategoryService {

    private CategoryRepository categoryRepository;
    private TaskRepository taskRepository;


    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository, TaskRepository taskRepository) {
        this.categoryRepository = categoryRepository;
        this.taskRepository = taskRepository;

    }

    @Override
    public List<CategoryDto> listAllCategories() {
        return categoryRepository.findAll().stream()
                .map(item -> CategoryDto.builder().
                        id(item.getIdCategory())
                        .name(item.getNameCategory())
                        .build()
                )
                .collect(Collectors.toList());
    }

    @Override
    public CategoryDto createCategory(String categoryName) {
        CategoryEntity newEntity = categoryRepository.save(CategoryEntity.builder().nameCategory(categoryName).build());
        return CategoryDto.builder()
                .id(newEntity.getIdCategory())
                .name(newEntity.getNameCategory())
                .build();
    }

    @Transactional
    @Override
    public void deleteCategoryById(Long idCategory) {
    	taskRepository.updateTaskCategoryToNull(idCategory);
        categoryRepository.deleteById(idCategory);
    }
}
