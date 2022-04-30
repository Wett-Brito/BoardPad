package com.boardpad.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.boardpad.entity.Task;
import com.boardpad.repository.TaskRepository;

@Service
public class TaskService {

	@Autowired
	TaskRepository repository;
	
	public List<Task> getAll(){
		return repository.findAll();
	}
	
	
	public Task getById(Long id){
		System.out.println(id);
		Optional<Task> obj =  repository.findById(id);
		return obj.get();
	}
	
}
