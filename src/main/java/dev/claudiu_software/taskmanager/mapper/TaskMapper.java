package dev.claudiu_software.taskmanager.mapper;

import dev.claudiu_software.taskmanager.dto.TaskRequest;
import dev.claudiu_software.taskmanager.dto.TaskResponse;
import dev.claudiu_software.taskmanager.entity.Task;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TaskMapper {

    public Task toEntity(TaskRequest request){
        return Task.builder()
                .title(request.title())
                .description(request.description())
                .completed(request.completed() != null ? request.completed() : false)
                .build();
    }

    public TaskResponse toResponse(Task entity){
        return TaskResponse.builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .description(entity.getDescription())
                .completed(entity.getCompleted())
                .createdAt(entity.getCreatedAt())
                .build();
    }

    public void updateEntityFromRequest(Task entity, TaskRequest request){
        entity.setTitle(request.title());
        entity.setDescription(request.description());
        entity.setCompleted(request.completed());
    }

    public List<TaskResponse> toResponseList(List<Task> tasks){
       return tasks.stream().map(this::toResponse).toList();
    }
}
