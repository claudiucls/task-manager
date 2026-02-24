package dev.claudiu_software.taskmanager.controller;

import dev.claudiu_software.taskmanager.entity.Task;
import dev.claudiu_software.taskmanager.repository.TaskRepository;
import dev.claudiu_software.taskmanager.service.TaskService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/tasks")
public class TaskController {

   private final TaskService taskService;

   public TaskController(TaskService taskService){
       this.taskService = taskService;
   }

    @GetMapping
    public List<Task> getAllTasks() {
        return taskService.getAllTasks();
    }

    @GetMapping("/{id}")
    public Task getTaskById(@PathVariable Long id){
        return taskService.getTaskById(id);

    }
    @PostMapping
    public ResponseEntity<Task> createTask(@RequestBody Task task) {
        Task savedTask = taskService.createTask(task);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedTask);
    }

    @PutMapping("/{id}")
    public Task updateTask(@PathVariable Long id, @RequestBody Task updatedTask ) {
        return taskService.updateTask(id, updatedTask);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id){
         taskService.deleteTask(id);
         return ResponseEntity.ok().build();

    }

    @GetMapping("/completed/{status}")
    public List<Task> getTasksByCompletions(@PathVariable boolean status)  {
       return taskService.getTasksByCompletionsStatus(status);
    }

    @GetMapping("/search")
    public List<Task> getTasksByTitle(@RequestParam String title){
       return taskService.searchTasksByTitle(title);
    }

}
