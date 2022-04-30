package com.boardpad.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.boardpad.entity.Board;
import com.boardpad.repository.BoardRespository;

@Service
public class BoardService {

	@Autowired
	BoardRespository repository;
	
	public List<Board> getAll(){
		return repository.findAll();
	}
	
}
