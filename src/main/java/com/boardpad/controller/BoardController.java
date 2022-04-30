package com.boardpad.controller;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.boardpad.entity.Board;
import com.boardpad.service.BoardService;

@RestController
@RequestMapping(value = "/")
public class BoardController {

	@Autowired
	BoardService service;
	
	@GetMapping
	public ResponseEntity<Board> getNewBoard(){
		Board board = service.getNewBoard();
		return ResponseEntity.ok().body(board);
		
	}
	
	@GetMapping(value = "/boards")
	public ResponseEntity<List<Board>> getAll(){
		List<Board> obj = service.getAll();
		
		return ResponseEntity.ok().body(obj);
	}
	
	@GetMapping(value = "/{code}")
	public ResponseEntity<Board> getBoardByCode(@PathVariable String code){
		Board board = service.getOrCreateBoard(code);
		return ResponseEntity.ok().body(board);
		
	}
	
	@PostMapping(value = "/boards")
	public ResponseEntity<Board> insert(@RequestBody Board board){
		Board obj = service.insert(board);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
		return ResponseEntity.created(uri).body(obj);
	}
	
	@PutMapping(value = "/{code}")
	public ResponseEntity<Board> update(@PathVariable String code, @RequestBody Board board){
		board.setCode(code);
		Board obj = service.update(board);
		return ResponseEntity.ok().body(obj);
	}
}
