package br.com.boardpadbackend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.boardpadbackend.dto.BoardDto;
import br.com.boardpadbackend.entity.BoardEntity;
import br.com.boardpadbackend.service.BoardService;
import br.com.boardpadbackend.utils.CodeGenerator;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RequestMapping(path = "/")
@RestController
@Api(value = "Board controller", tags = {"Boards"})
public class BoardController {
    private BoardService BoardService;

    @Autowired
    public BoardController(BoardService BoardService) {
        this.BoardService = BoardService;
    }

    @ApiOperation("Create a board passing a code")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 500, message = "Internal server error. Board wasn't created")
    })
    @GetMapping(value = "/{code}")
    public ResponseEntity<BoardDto> FindOrCreateBoard (@PathVariable String code){
        return ResponseEntity.ok().body(BoardService.createBoard(code)) ;
    }
    
    
    @ApiOperation("Create a board passing a code")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 500, message = "Internal server error. Board wasn't created")
    })
    @GetMapping()
    public ResponseEntity<BoardDto> createBoardWithRandomCode (){
		CodeGenerator passwordGenerator = new CodeGenerator.codeGeneratorBuilder()
        .useDigits(true)
        .useLower(true)
        .useUpper(true)
        .build();
    	
        return ResponseEntity.ok().body(BoardService.createBoard(passwordGenerator.generate(10))) ;
    }	    
    
}
