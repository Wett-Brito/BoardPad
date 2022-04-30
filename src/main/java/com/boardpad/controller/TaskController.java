package com.boardpad.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.boardpad.entity.Task;
import com.boardpad.service.TaskService;

@RestController
@RequestMapping(value = "/tasks")
public class TaskController {

	@Autowired
	TaskService taskService;
	
	@GetMapping
	public ResponseEntity<List<Task>> getAll(){
		List<Task> obj = taskService.getAll();
		return ResponseEntity.ok().body(obj);
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<Task> getById(@PathVariable Long id){
		Task obj = taskService.getById(id);
		return ResponseEntity.ok().body(obj);
	}
	
}
