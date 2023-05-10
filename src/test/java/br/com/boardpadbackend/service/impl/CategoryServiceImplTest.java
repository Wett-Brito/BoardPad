package br.com.boardpadbackend.service.impl;

import br.com.boardpadbackend.exceptions.BadRequestException;
import br.com.boardpadbackend.exceptions.NotFoundException;
import br.com.boardpadbackend.mockedclasses.BoardEntityAndDto;
import br.com.boardpadbackend.repositories.CategoryRepository;
import br.com.boardpadbackend.repositories.TaskRepository;
import br.com.boardpadbackend.service.BoardService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static br.com.boardpadbackend.mockedclasses.CategoryEntityAndDto.*;

@ExtendWith(SpringExtension.class)
class CategoryServiceImplTest {
    @Mock private CategoryRepository categoryRepository;
    @Mock private BoardService boardService;
    @Mock private TaskRepository taskRepository;
    @InjectMocks private CategoryServiceImpl categoryService;

    @Test
    public void listAllCategories_WhenSuccess () {
        when(categoryRepository.findAllByBoardCode(eq("test-board")))
                .thenReturn(List.of(MOCKED_CATEGORY_ENTITY));

        var actualCategoryList = categoryService.listAllCategories("test-board");

        assertEquals(List.of(MOCKED_CATEGORY_DTO), actualCategoryList);
    }
    @Test
    public void createCategory_whenSuccess() {
        when(boardService.findBoardByBoardCode(eq("board-test"))).thenReturn(BoardEntityAndDto.BOARD_DTO);
        when(categoryRepository.save(any())).thenReturn(MOCKED_CATEGORY_ENTITY);

        var actualCategory = categoryService.createCategory("board-test", "Category Test");
        assertEquals(MOCKED_CATEGORY_DTO, actualCategory);
    }
    @Test
    public void createCategory_whenBoardDoesntExists() {
        when(boardService.findBoardByBoardCode(eq("board-test")))
                .thenThrow(new BadRequestException("The board [board-test] doesn't exists."));
        assertThrows(BadRequestException.class,
                ()-> categoryService.createCategory("board-test", "Category Test")
        );
    }

    @Test
    public void deleteCategoryById_whenSuccess() {
        when(categoryRepository.findByBoardCodeAndCategoryId(eq("board-test"), eq(1L)))
                .thenReturn(Optional.of(MOCKED_CATEGORY_ENTITY));
        doNothing().when(taskRepository).updateTaskCategoryToNull(eq(1L));
        doNothing().when(categoryRepository).delete(eq(MOCKED_CATEGORY_ENTITY));

        assertDoesNotThrow(()-> categoryService.deleteCategoryById("board-test", 1L));
    }

    @Test
    public void deleteCategoryById_whenCategoryNotFound() {
        when(categoryRepository.findByBoardCodeAndCategoryId(eq("board-test"), eq(1L)))
                .thenReturn(Optional.empty());

        assertThrows(NotFoundException.class,
                ()-> categoryService.deleteCategoryById("board-test", 1L));
    }
}