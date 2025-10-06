package com.clarity.task_service.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDate;

@Data
public class CreateTaskRequest {
    
    @NotBlank(message = "Title is required")
    private String title;
    
    private String description;
    
    private LocalDate dueDate;
    
    private String priority; // "high", "medium", "low"
}