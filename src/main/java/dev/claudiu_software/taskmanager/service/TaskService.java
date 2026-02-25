package dev.claudiu_software.taskmanager.service;

import dev.claudiu_software.taskmanager.dto.TaskRequest;
import dev.claudiu_software.taskmanager.dto.TaskResponse;
import dev.claudiu_software.taskmanager.entity.Task;
import dev.claudiu_software.taskmanager.exception.TaskNotFoundException;
import dev.claudiu_software.taskmanager.mapper.TaskMapper;
import dev.claudiu_software.taskmanager.repository.TaskRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class TaskService {

    private TaskRepository taskRepository;
    private TaskMapper taskMapper;

    public TaskService(TaskRepository taskRepository, TaskMapper taskMapper) {
        this.taskRepository = taskRepository;
        this.taskMapper = taskMapper;
    }

    public List<TaskResponse> getAllTasks(){
        return taskMapper.toResponseList(taskRepository.findAll());
    }

    public TaskResponse getTaskById(Long id){
        return taskMapper.toResponse(taskRepository.findById(id)
                .orElseThrow(()-> new TaskNotFoundException(id)));
    }

    public TaskResponse createTask(TaskRequest task){
        Task entityTask = taskMapper.toEntity(task);
        Task savedTask = taskRepository.save(entityTask);
        return taskMapper.toResponse(savedTask);
    }

    public TaskResponse updateTask(Long id, TaskRequest updatedTask){
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException(id));
        taskMapper.updateEntityFromRequest(task, updatedTask);
        return taskMapper.toResponse(taskRepository.save(task));
    }

    public void deleteTask(Long id){
        Task task = taskRepository.findById(id)
                .orElseThrow(()-> new TaskNotFoundException(id));
        taskRepository.delete(task);
    }

    public List<TaskResponse> getTasksByCompletionsStatus(boolean status){
        return taskMapper.toResponseList(taskRepository.findByCompleted(status));
    }

    public List<TaskResponse> searchTasksByTitle(String title){
        return taskMapper.toResponseList(taskRepository.findByTitleContainingIgnoreCase(title));
    }

}
