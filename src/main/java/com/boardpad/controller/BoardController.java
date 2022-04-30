package com.boardpad.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.boardpad.entity.Board;
import com.boardpad.service.BoardService;

@RestController
@RequestMapping(value = "/boards")
public class BoardController {

	@Autowired
	BoardService service;
	
	@GetMapping
	public ResponseEntity<List<Board>> getAll(){
		List<Board> obj = service.getAll();
		
		return ResponseEntity.ok().body(obj);
	}
	
}
