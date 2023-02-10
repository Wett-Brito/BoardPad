package br.com.boardpadbackend.controllers;

import br.com.boardpadbackend.dto.CategoryDto;
import br.com.boardpadbackend.dto.GenericResponseDTO;
import br.com.boardpadbackend.exceptions.InternalServerErrorException;
import br.com.boardpadbackend.service.CategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.util.List;

@RequestMapping(path = "/category")
@RestController
@Api(value = "Task Categories", tags = {"Category"})
public class CategoryController {
    private CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    @ApiOperation(value = "Lists all categories available in a board")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 204, message = "No categories available"),
            @ApiResponse(code = 500, message = "Internal server error. Please try again")
    })
    public ResponseEntity<GenericResponseDTO<List<CategoryDto>>> listAllCategoriesOfBoard (@RequestParam("board-code")String boardCode) {
        List<CategoryDto> responseList = categoryService.listAllCategories(boardCode);

        return (responseList.size() > 0) ?
                ResponseEntity.ok().body(GenericResponseDTO.<List<CategoryDto>>builder()
                        .status("OK")
                        .response(responseList)
                        .build()) :
                ResponseEntity.status(404).body(GenericResponseDTO.<List<CategoryDto>>builder()
                        .status("NOK")
                        .message("There is no categories registered at this board")
                        .build());
    }
    @ApiOperation("Create a new task Category")
    @ApiResponses({
            @ApiResponse( code= 201, message = "Category created successfully"),
            @ApiResponse( code= 500, message = "Internal Server Error. Category wasn't created.")
    })
    @PostMapping
    public ResponseEntity<CategoryDto> createCategory (@RequestParam("board-code")String boardCode, String newCategoryName) {
        CategoryDto newCategory = categoryService.createCategory(boardCode, newCategoryName);
        if (newCategory == null || newCategory.getName().isEmpty())
            throw new InternalServerErrorException("Internal error when tried to create a category");
        return ResponseEntity.status(201).body(newCategory);
    }
    @ApiOperation("Delete a task Category")
    @ApiResponses({
            @ApiResponse( code= 204, message = "Category deleted successfully"),
            @ApiResponse( code= 500, message = "Internal Server Error. Category wasn't deleted.")
    })
    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteCategoryById (@PathVariable("board-code") String boardCode,
                                                    @PathVariable("id") Long idCategory) {
        categoryService.deleteCategoryById(boardCode, idCategory);
        
        return ResponseEntity.noContent().build();
    }

}