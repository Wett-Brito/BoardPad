package br.com.boardpadbackend.service.impl;

import br.com.boardpadbackend.dto.CategoryDto;
import br.com.boardpadbackend.entity.BoardEntity;
import br.com.boardpadbackend.entity.CategoryEntity;
import br.com.boardpadbackend.exceptions.NotFoundException;
import br.com.boardpadbackend.repositories.CategoryRepository;
import br.com.boardpadbackend.repositories.TaskRepository;
import br.com.boardpadbackend.service.BoardService;
import br.com.boardpadbackend.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

@Service
public class CategoryServiceImpl implements CategoryService {

    private CategoryRepository categoryRepository;
    private BoardService boardService;
    private TaskRepository taskRepository;


    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository,
                               BoardService boardService,
                               TaskRepository taskRepository) {
        this.categoryRepository = categoryRepository;
        this.boardService = boardService;
        this.taskRepository = taskRepository;

    }

    @Override
    public List<CategoryDto> listAllCategories(String boardCode) {
        return categoryRepository.findAllByBoardCode(boardCode).stream()
                .map(item -> CategoryDto.builder().
                        id(item.getIdCategory())
                        .name(item.getNameCategory())
                        .build()
                )
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public CategoryDto createCategory(String boardCode, String categoryName) {
        BoardEntity foundBoard = boardService.findBoardByBoardCode(boardCode);

        CategoryEntity newEntity = categoryRepository.save(
                CategoryEntity.builder()
                        .board(foundBoard)
                        .nameCategory(categoryName)
                        .build()
        );
        return CategoryDto.builder()
                .id(newEntity.getIdCategory())
                .name(newEntity.getNameCategory())
                .build();
    }

    @Transactional
    @Override
    public void deleteCategoryById(String boardCode, Long idCategory) {
        Optional <CategoryEntity> foundCategory = categoryRepository.findByBoardCodeAndCategoryId(boardCode, idCategory);
        if(foundCategory.isEmpty()) throw new NotFoundException("Category not found.");

    	taskRepository.updateTaskCategoryToNull(idCategory);
        categoryRepository.delete(foundCategory.get());
    }
}
