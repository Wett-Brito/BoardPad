package br.com.boardpadbackend.service.impl;

import br.com.boardpadbackend.repositories.BoardRepository;
import br.com.boardpadbackend.repositories.CategoryRepository;
import br.com.boardpadbackend.repositories.TaskRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static br.com.boardpadbackend.mockedclasses.CategoryEntityAndDto.*;

@ExtendWith(SpringExtension.class)
class CategoryServiceImplTest {
    @Mock private CategoryRepository categoryRepository;
    @Mock private BoardRepository boardRepository;
    @Mock private TaskRepository taskRepository;
    @InjectMocks private CategoryServiceImpl categoryService;

    @Test
    public void listAllCategories_WhenSuccess () {
        when(categoryRepository.findAllByBoardCode(eq("test-board")))
                .thenReturn(List.of(MOCKED_CATEGORY_ENTITY));

        var actualCategoryList = categoryService.listAllCategories("test-board");

        assertEquals(List.of(MOCKED_CATEGORY_DTO), actualCategoryList);
    }
}