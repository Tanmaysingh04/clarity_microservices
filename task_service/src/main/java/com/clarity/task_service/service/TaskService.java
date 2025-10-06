package com.clarity.task_service.service;

import com.clarity.task_service.dto.CreateTaskRequest;
import com.clarity.task_service.dto.TaskDTO;
import com.clarity.task_service.dto.UpdateTaskRequest;
import com.clarity.task_service.model.Task;
import com.clarity.task_service.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TaskService {
    
    private final TaskRepository taskRepository;
    
    public String createTask(String userId, CreateTaskRequest request) {
        Task task = new Task();
        task.setUserId(userId);
        task.setTitle(request.getTitle());
        task.setDescription(request.getDescription());
        task.setDueDate(request.getDueDate());
        task.setPriority(request.getPriority() != null ? request.getPriority() : "medium");
        task.setCompleted(false);
        task.setCreatedAt(LocalDateTime.now());
        task.setUpdatedAt(LocalDateTime.now());
        
        Task savedTask = taskRepository.save(task);
        return savedTask.getId();
    }
    
    public List<TaskDTO> getAllTasks(String userId) {
        List<Task> tasks = taskRepository.findByUserId(userId);
        return tasks.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    public List<TaskDTO> getPendingTasks(String userId) {
        List<Task> tasks = taskRepository.findByUserIdAndCompletedFalse(userId);
        return tasks.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    public TaskDTO getTaskById(String userId, String taskId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task not found"));
        
        if (!task.getUserId().equals(userId)) {
            throw new RuntimeException("Unauthorized");
        }
        
        return convertToDTO(task);
    }
    
    public void updateTask(String userId, String taskId, UpdateTaskRequest request) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task not found"));
        
        if (!task.getUserId().equals(userId)) {
            throw new RuntimeException("Unauthorized");
        }
        
        if (request.getTitle() != null) {
            task.setTitle(request.getTitle());
        }
        if (request.getDescription() != null) {
            task.setDescription(request.getDescription());
        }
        if (request.getDueDate() != null) {
            task.setDueDate(request.getDueDate());
        }
        if (request.getPriority() != null) {
            task.setPriority(request.getPriority());
        }
        
        task.setUpdatedAt(LocalDateTime.now());
        taskRepository.save(task);
    }
    
    public void completeTask(String userId, String taskId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task not found"));
        
        if (!task.getUserId().equals(userId)) {
            throw new RuntimeException("Unauthorized");
        }
        
        task.setCompleted(true);
        task.setCompletedAt(LocalDateTime.now());
        task.setUpdatedAt(LocalDateTime.now());
        
        taskRepository.save(task);
    }
    
    public void deleteTask(String userId, String taskId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task not found"));
        
        if (!task.getUserId().equals(userId)) {
            throw new RuntimeException("Unauthorized");
        }
        
        taskRepository.delete(task);
    }
    
    private TaskDTO convertToDTO(Task task) {
        return new TaskDTO(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getDueDate(),
                task.getPriority(),
                task.isCompleted(),
                task.getCompletedAt(),
                task.getCreatedAt()
        );
    }
}