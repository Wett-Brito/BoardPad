package br.com.boardpadbackend.controllers;

import br.com.boardpadbackend.dto.CategoryDto;
import br.com.boardpadbackend.dto.GenericResponseDTO;
import br.com.boardpadbackend.exceptions.NotFoundException;
import br.com.boardpadbackend.mockedclasses.CategoryEntityAndDto;
import br.com.boardpadbackend.service.CategoryService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@WebMvcTest(CategoryController.class)
class CategoryControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private CategoryService categoryService;

    private final String URL_CATEGORY_CONTROLLER = "/category";

    /**
     * When the board has categories to be listed
     * @throws Exception
     */
    @Test
    public void listAllCategoriesOfBoard_WhenSuccess() throws Exception {
        var serviceResponse = List.of(CategoryEntityAndDto.MOCKED_CATEGORY_DTO);
        final var EXPECTED_RESPONSE = GenericResponseDTO.<List<CategoryDto>>builder()
                .status("OK")
                .response(serviceResponse)
                .build();
        when(categoryService.listAllCategories(eq("board-test")))
                .thenReturn(serviceResponse);

        mockMvc.perform(get(URL_CATEGORY_CONTROLLER).queryParam("board-code", "board-test"))
                .andExpectAll(
                        status().isOk(),
                        content().json(objectMapper.writeValueAsString(EXPECTED_RESPONSE))
                );
    }

    /**
     * When none category was created in the board yet
     * @throws Exception
     */
    @Test
    public void listAllCategoriesOfBoard_WhenFail() throws Exception {
        final var EXPECTED_RESPONSE = GenericResponseDTO.<List<CategoryDto>>builder()
                .status("NOK")
                .message("There is no categories registered at this board")
                .build();
        when(categoryService.listAllCategories(eq("board-test")))
                .thenReturn(new ArrayList<>());

        mockMvc.perform(get(URL_CATEGORY_CONTROLLER).queryParam("board-code", "board-test"))
                .andExpectAll(
                        status().isNotFound(),
                        content().json(objectMapper.writeValueAsString(EXPECTED_RESPONSE))
                );
    }
    /**
     * When a category can be created without errors
     * @throws Exception
     */
    @Test
    public void createCategory_WhenSuccess() throws Exception {
        final var SERVICE_RESPONSE = CategoryEntityAndDto.MOCKED_CATEGORY_DTO;
        when(categoryService.createCategory(eq("board-test"), eq("Category Test")))
                .thenReturn(SERVICE_RESPONSE);

        mockMvc.perform(post(URL_CATEGORY_CONTROLLER)
                        .queryParam("board-code", "board-test")
                        .queryParam("new-category-name", "Category Test"))
                .andExpectAll(
                        status().isCreated(),
                        content().json(objectMapper.writeValueAsString(SERVICE_RESPONSE))
                );
    }

    /**
     * When tries to create a category in a non-existent board
     * @throws Exception
     */
    @Test
    public void createCategory_WhenBoardIsNotCreated() throws Exception {
        final String EXCEPTION_MESSAGE = "The board [board-test] doesn't exists.";
        final var EXPECTED_RESPONSE = GenericResponseDTO.builder()
                .status("NOK")
                .message(EXCEPTION_MESSAGE)
                .build();
        when(categoryService.createCategory(eq("board-test"), eq("Category Test")))
                .thenThrow(new NotFoundException(EXCEPTION_MESSAGE));

        mockMvc.perform(post(URL_CATEGORY_CONTROLLER)
                        .queryParam("board-code", "board-test")
                        .queryParam("new-category-name", "Category Test"))
                .andExpectAll(
                        status().isNotFound(),
                        content().json(objectMapper.writeValueAsString(EXPECTED_RESPONSE))
                );
    }

    /**
     * When DELETES a category successfully
     * @throws Exception
     */
    @Test
    public void deleteCategoryById_WhenSuccess() throws Exception {
        doNothing().when(categoryService).deleteCategoryById(eq("board-test"), eq(1L));

        mockMvc.perform(delete(URL_CATEGORY_CONTROLLER + "/1")
                        .queryParam("board-code", "board-test"))
                .andExpect(status().isNoContent());
    }

    /**
     * When tries to DELETE a non-existent category
     * @throws Exception
     */
    @Test
    public void deleteCategoryById_WhenCategoryDoesntExists() throws Exception {
        doThrow(NotFoundException.class).when(categoryService).deleteCategoryById(eq("board-test"), eq(1L));

        mockMvc.perform(delete(URL_CATEGORY_CONTROLLER + "/1")
                        .queryParam("board-code", "board-test"))
                .andExpect(status().isNotFound());
    }
}