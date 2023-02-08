package br.com.boardpadbackend.service.impl;

import br.com.boardpadbackend.dto.CategoryDto;
import br.com.boardpadbackend.entity.BoardEntity;
import br.com.boardpadbackend.entity.CategoryEntity;
import br.com.boardpadbackend.exceptions.BadRequestException;
import br.com.boardpadbackend.exceptions.NotFoundException;
import br.com.boardpadbackend.repositories.BoardRepository;
import br.com.boardpadbackend.repositories.CategoryRepository;
import br.com.boardpadbackend.repositories.TaskRepository;
import br.com.boardpadbackend.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

@Service
public class CategoryServiceImpl implements CategoryService {

    private CategoryRepository categoryRepository;
    private BoardRepository boardRepository;
    private TaskRepository taskRepository;


    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository,
                               BoardRepository boardRepository,
                               TaskRepository taskRepository) {
        this.categoryRepository = categoryRepository;
        this.boardRepository = boardRepository;
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

    @Override
    public CategoryDto createCategory(String boardCode, String categoryName) {
        Optional<BoardEntity> foundBoard = boardRepository.findByCodeBoard(boardCode);
        if(foundBoard.isEmpty()) throw new BadRequestException("The board wasn't created. Please create a board before try create a category");

        CategoryEntity newEntity = CategoryEntity.builder()
                .board(foundBoard.get())
                .nameCategory(categoryName)
                .build();
        categoryRepository.save(newEntity);
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
