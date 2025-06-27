package com.planningapp.service;

import com.planningapp.entity.Task;
import com.planningapp.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskService {
    @Autowired
    private TaskRepository taskRepository;

    public List<Task> findAll() {
        return taskRepository.findAll();
    }

    public Task save(Task task) {
        return taskRepository.save(task);
    }

    public void delete(Long id) {
        taskRepository.deleteById(id);
    }
    
    public void saveAll(List<Task> tarefas) {
    	taskRepository.saveAll(tarefas);
    }
    
    public Optional<Task> findFirstByEstimadaFalseOrderByIdAsc() {
    	return taskRepository.findFirstByEstimadaFalseOrderByIdAsc();
    }
    
    public Optional<Task> findById(Long id) {
    	return taskRepository.findById(id);    	
    }
}
