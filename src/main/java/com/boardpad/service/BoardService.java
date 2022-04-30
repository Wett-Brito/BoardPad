package com.boardpad.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.boardpad.entity.Board;
import com.boardpad.entity.Task;
import com.boardpad.repository.BoardRespository;
import com.boardpad.utils.CodeGenerator;

@Service
public class BoardService {

	@Autowired
	BoardRespository repository;
	
	@Autowired
	TaskService taskService;
	
	public List<Board> getAll(){
		return repository.findAll();
	}
	
	public Board getOrCreateBoard(String code) {
		List<Board> list = getAll();
		for (Board b : list) {
			if(b.getCode().equals(code)) {
				return b;
			}
		}
		
		Board obj = new Board(code, "New board");
		
		repository.save(obj);
		
		return obj;
	}
	
	public Board getNewBoard() {
		CodeGenerator passwordGenerator = new CodeGenerator.codeGeneratorBuilder()
		        .useDigits(true)
		        .useLower(true)
		        .useUpper(true)
		        .build();
		String code = passwordGenerator.generate(10);
		
		Board obj = new Board(code, "New board");
		
		repository.save(obj);
		
		return obj;

	}
	
	public Board insert(Board board) {
		Board entity = findByCode(board.getCode());
		List<Task> listTask = board.getTasks();
		List<Task> list = new ArrayList<>();		
		
		for (Task task : listTask) {
			Task obj = taskService.insert(new Task(task.getTitle(), task.getCategory(), task.getDescription(), task.getStatus(), entity));
			list.add(taskService.getById(obj.getId()));
			
		}
				
		entity.getTasks().addAll(list);
		
		return repository.save(entity);
	}
	
	public Board findByCode(String code) {
		List<Board> list = getAll();
		for (Board b : list) {
			if(b.getCode().equals(code)) {
				return b;
			}
		}
		
		throw new RuntimeException();
	}
	
	public Board update(Board board) {
		Board entity = findByCode(board.getCode());
		
		updateData(board, entity);
		
		return entity;
	}
	
	private void updateData(Board board, Board entity) {
		entity.setName(board.getName());
	}

}
