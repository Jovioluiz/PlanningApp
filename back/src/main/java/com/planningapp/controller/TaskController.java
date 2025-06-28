package com.planningapp.controller;

import com.planningapp.entity.Task;
import com.planningapp.service.TaskService;

import java.util.List;
import java.util.Optional;

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
    
    @PostMapping("/ativa")
    public ResponseEntity<?> getTarefaAtiva(@RequestParam String usuario) {
        if (!usuario.equalsIgnoreCase("admin")) {
            // somente se o admin já liberou uma tarefa (ex: estimada = false e liberada = true)
            Optional<Task> tarefa = taskService.listarTarefasLiberadas();
            return tarefa.map(ResponseEntity::ok)
                         .orElse(ResponseEntity.noContent().build());
        }

        // Admin pode ver a próxima da fila
        Optional<Task> tarefa = taskService.listarTarefasAdmin();
        return tarefa.map(ResponseEntity::ok)
                     .orElse(ResponseEntity.noContent().build());
    }
    
    @GetMapping("/liberadas")
    public ResponseEntity<List<Task>> listarLiberadas() {
        return ResponseEntity.ok(taskService.listarTarefasLiberadasParaEstimativa());
    }

    @PutMapping("/{id}/liberar")
    public ResponseEntity<?> liberarTarefa(@PathVariable Long id) {
    	taskService.liberarTarefa(id);
        return ResponseEntity.ok().build();
    }

   
    
}
