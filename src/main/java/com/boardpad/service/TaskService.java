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
		Optional<Task> obj =  repository.findById(id);
		return obj.get();
	}
	
	public Task insert(Task task) {
		return repository.save(task);
	}
	
	public Task updata(Long id,Task task) {
		Task entity = getById(id);
		
		updateData(task, entity);
		
		return repository.save(entity);
	}
	
	private void updateData(Task task, Task entity) {
		entity.setTitle(task.getTitle());
		entity.setCategory(task.getCategory());
		entity.setDescription(task.getDescription());
		entity.setStatus(task.getStatus());
	}
	
	public void delete(Long id) {
		repository.deleteById(id);
	}
	
}
