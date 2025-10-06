package com.clarity.task_service.controller;

import com.clarity.task_service.dto.CreateTaskRequest;
import com.clarity.task_service.dto.TaskDTO;
import com.clarity.task_service.dto.UpdateTaskRequest;
import com.clarity.task_service.service.TaskService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/tasks")
@RequiredArgsConstructor
public class TaskController {
    
    private final TaskService taskService;
    
    @PostMapping
    public ResponseEntity<?> createTask(
            @RequestHeader("X-User-Id") String userId,
            @Valid @RequestBody CreateTaskRequest request) {
        try {
            String taskId = taskService.createTask(userId, request);
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Task created successfully");
            response.put("taskId", taskId);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }
    
    @GetMapping
    public ResponseEntity<?> getAllTasks(@RequestHeader("X-User-Id") String userId) {
        try {
            List<TaskDTO> tasks = taskService.getAllTasks(userId);
            return ResponseEntity.ok(tasks);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }
    
    @GetMapping("/pending")
    public ResponseEntity<?> getPendingTasks(@RequestHeader("X-User-Id") String userId) {
        try {
            List<TaskDTO> tasks = taskService.getPendingTasks(userId);
            return ResponseEntity.ok(tasks);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }
    
    @GetMapping("/{taskId}")
    public ResponseEntity<?> getTask(
            @RequestHeader("X-User-Id") String userId,
            @PathVariable String taskId) {
        try {
            TaskDTO task = taskService.getTaskById(userId, taskId);
            return ResponseEntity.ok(task);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }
    }
    
    @PutMapping("/{taskId}")
    public ResponseEntity<?> updateTask(
            @RequestHeader("X-User-Id") String userId,
            @PathVariable String taskId,
            @Valid @RequestBody UpdateTaskRequest request) {
        try {
            taskService.updateTask(userId, taskId, request);
            Map<String, String> response = new HashMap<>();
            response.put("message", "Task updated successfully");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }
    
    @PostMapping("/{taskId}/complete")
    public ResponseEntity<?> completeTask(
            @RequestHeader("X-User-Id") String userId,
            @PathVariable String taskId) {
        try {
            taskService.completeTask(userId, taskId);
            Map<String, String> response = new HashMap<>();
            response.put("message", "Task marked as completed");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }
    
    @DeleteMapping("/{taskId}")
    public ResponseEntity<?> deleteTask(
            @RequestHeader("X-User-Id") String userId,
            @PathVariable String taskId) {
        try {
            taskService.deleteTask(userId, taskId);
            Map<String, String> response = new HashMap<>();
            response.put("message", "Task deleted successfully");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }
}