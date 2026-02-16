package dev.claudiu_software.taskmanager.controller;

import dev.claudiu_software.taskmanager.entity.Task;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/tasks")
public class TaskController {

    //temporary in-memory task list
    private List<Task> tasks = new ArrayList<>();
    private Long nextId = 1L;

    @GetMapping
    public List<Task> getAllTasks() {
        return tasks;
    }

    @GetMapping("/{id}")
    public Task getTaskById(@PathVariable Long id){
        return tasks.stream()
                .filter(task -> task.getId().equals(id))
                .findFirst()
                .orElse(null);
    }
    @PostMapping
    public Task createTask(@RequestBody Task task) {
        task.setId(nextId++);
        task.setCreatedAt(LocalDateTime.now());
        task.setCompleted(false);
        tasks.add(task);
        return task;
    }

    @PutMapping("/{id}")
    public Task updateTask(@PathVariable Long id, @RequestBody Task updatedTask ) {
        for(int i=0; i < tasks.size(); i++){
            Task task = tasks.get(i);
            if(task.getId().equals(id)){
                updatedTask.setId(task.getId());
                updatedTask.setCreatedAt(task.getCreatedAt());
                tasks.set(i, updatedTask);
                return updatedTask;
            }
        }
        return null;
    }

    @DeleteMapping("/{id}")
    public void deleteTask(@PathVariable Long id){
        tasks.removeIf(task -> task.getId().equals(id));
    }
}
