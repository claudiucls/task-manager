package dev.claudiu_software.taskmanager.controller;

import dev.claudiu_software.taskmanager.entity.Task;
import dev.claudiu_software.taskmanager.repository.TaskRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/tasks")
public class TaskController {

   private final TaskRepository taskRepository;

   public TaskController(TaskRepository taskRepository){
       this.taskRepository = taskRepository;
   }

    @GetMapping
    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Task> getTaskById(@PathVariable Long id){
        return taskRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());

    }
    @PostMapping
    public ResponseEntity<Task> createTask(@RequestBody Task task) {
        Task savedTask = taskRepository.save(task);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedTask);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Task> updateTask(@PathVariable Long id, @RequestBody Task updatedTask ) {
        return taskRepository.findById(id)
                .map(task -> {
                    task.setTitle(updatedTask.getTitle());
                    task.setDescription(updatedTask.getDescription());
                    task.setCompleted(updatedTask.getCompleted());
                    Task savedTask = taskRepository.save(task);
                    return ResponseEntity.ok(savedTask);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteTask(@PathVariable Long id){
        return taskRepository.findById(id)
                .map(task -> {
                    taskRepository.delete(task);
                    return ResponseEntity.noContent().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/completed/{status}")
    public List<Task> getTasksByCompletions(@PathVariable boolean status)  {
       return taskRepository.findByCompleted(status);
    }

    @GetMapping("/search")
    public List<Task> getTasksByTitle(@RequestParam String title){
       return taskRepository.findByTitleContainingIgnoreCase(title);
    }

}
