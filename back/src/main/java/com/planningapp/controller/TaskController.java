package com.planningapp.controller;

import com.planningapp.entity.Task;
import com.planningapp.service.TaskService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tasks")
@CrossOrigin
public class TaskController {
    @Autowired
    private TaskService taskService;

    @GetMapping
    public List<Task> listarTarefas() {
        return taskService.findAll();
    }

    @PostMapping("/criarTask")
    public Task create(@RequestBody Task task) {
        return taskService.save(task);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        taskService.delete(id);
    }
    
    @PostMapping("/importar")
    public ResponseEntity<Void> importarTarefas(@RequestBody List<Task> tarefas) {
        taskService.saveAll(tarefas);
        return ResponseEntity.ok().build();
    }
    
    @GetMapping("/ativa")
    public ResponseEntity<?> tarefaAtiva() {
        return taskService.findFirstByEstimadaFalseOrderByIdAsc()
                .map(tarefa -> ResponseEntity.ok(tarefa))
                .orElse(ResponseEntity.noContent().build());
    }
}
