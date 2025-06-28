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
    
    public Optional<Task> listarTarefasAdmin() {
    	return taskRepository.findFirstByEstimadaFalseOrderByIdAsc();
    }
    
    public Optional<Task> listarTarefasLiberadas() {
    	return taskRepository.findFirstByEstimadaFalseAndLiberadaTrueOrderByIdAsc();
    }
    
    public Optional<Task> findById(Long id) {
    	return taskRepository.findById(id);    	
    }
    
    public List<Task> listarTarefasLiberadasParaEstimativa() {
        return taskRepository.findByEstimadaFalseAndLiberadaTrueOrderByIdAsc();
    }
    
    public void liberarTarefa(Long id) {
        Task tarefa = taskRepository.findById(id).orElseThrow();
        tarefa.setLiberada(true);
        taskRepository.save(tarefa);
    }

}
