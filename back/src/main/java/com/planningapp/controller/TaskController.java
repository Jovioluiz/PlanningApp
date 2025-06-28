package com.planningapp.controller;

import com.planningapp.entity.Task;
import com.planningapp.service.TaskService;

import java.util.List;
import java.util.Map;
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
    
    @GetMapping("/{id}")
    public ResponseEntity<Task> getById(@PathVariable Long id) {
        return taskService.findById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/criarTask")
    public Task create(@RequestBody Task task) {
        return taskService.save(task);
    }

    @DeleteMapping("/DeletarTask/{id}")
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
        List<Task> tarefa = taskService.listarTarefasAdmin();
        
        if (tarefa.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(tarefa);
    }
    
    
    @GetMapping("/liberadas")
    public ResponseEntity<List<Task>> listarLiberadas() {
        return ResponseEntity.ok(taskService.listarTarefasLiberadasParaEstimativa());
    }

    @PostMapping("/{id}/liberar")
    public ResponseEntity<?> liberarTarefa(@PathVariable("id") Long taskId) {
    	var liberada = taskService.liberarTarefa(taskId);
    	
    	if (liberada) {
    		return ResponseEntity.ok(Map.of("success", true, "message", "Tarefa liberada"));
    	} else {
    		return ResponseEntity.status(404).body(Map.of("success", false, "message", "Tarefa não encontrada"));
    	}
    }

    @GetMapping("/admin")
    public List<Task> listarParaAdmin() {
        return taskService.listarTarefasAdmin();
    }
    
    // Tarefa atual em votação (liberada)
    @GetMapping("/emvotacao")
    public ResponseEntity<?> tarefaEmVotacao() {
        return taskService.findLiberada()
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.noContent().build());
    }
    
    // Tarefas já estimadas
    @GetMapping("/votadas")
    public ResponseEntity<?> tarefasVotadas() {
        return ResponseEntity.ok(taskService.findEstimadas());
    }
   
    
}
