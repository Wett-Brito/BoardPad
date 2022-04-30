package com.boardpad.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.boardpad.entity.Board;
import com.boardpad.entity.Task;
import com.boardpad.enuns.StatusTask;
import com.boardpad.repository.BoardRespository;
import com.boardpad.repository.TaskRepository;

@Configuration
@Profile("test")
public class TestConfig implements CommandLineRunner{

	@Autowired
	TaskRepository taskRepository;
	
	@Autowired
	BoardRespository boardRespository;

	@Override
	public void run(String... args) throws Exception {

		Board b1 = new Board(null, "ITgGLkkI", "Primeiro Board");
		Board b2 = new Board(null, "yq7gOxiW", "Segundo Board");
		Board b3 = new Board(null, "v0eatukL", "Terceiro Board");
		boardRespository.saveAll(Arrays.asList(b1, b2, b3));
		
		Task t1 = new Task(null, "Comer de forma mais saudável", "Alimentação", "Tarefa que criei para ver se consigo melhorara minha forma de alimentação", StatusTask.TODO, b1);
		Task t2 = new Task(null, "Praticar mais atividades fisicas", "Bem estar", "Tentativa de melhor condicionamento fisico", StatusTask.DOING, b1);
		
		Task t3 = new Task(null, "Comer mais batata frita", "Alimentação", "Tentativa de engordar hueheue", StatusTask.DONE, b2);
		
		
		taskRepository.saveAll(Arrays.asList(t1, t2, t3));
		
		
		
	}
	
	
}
