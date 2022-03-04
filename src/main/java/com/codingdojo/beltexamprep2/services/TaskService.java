package com.codingdojo.beltexamprep2.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.codingdojo.beltexamprep2.models.Task;
import com.codingdojo.beltexamprep2.repositories.TaskRepository;

@Service
public class TaskService {
	
	private final TaskRepository taskRepo;
	public TaskService(TaskRepository taskRepo) {
		this.taskRepo=taskRepo;
	}
		
	public Task createTask(Task task) {
		return taskRepo.save(task);		
	}
	
	public List<Task> retrieveTasks(Long id){
		return taskRepo.findAllByProject_IdOrderByCreatedAtDesc(id);
	}
}
