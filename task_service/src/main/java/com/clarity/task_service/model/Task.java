package com.clarity.task_service.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "tasks")
public class Task {
    
    @Id
    private String id;
    
    @Indexed
    private String userId;
    
    private String title;
    
    private String description;
    
    private LocalDate dueDate;
    
    private String priority; // "high", "medium", "low"
    
    private boolean completed = false;
    
    private LocalDateTime completedAt;
    
    private LocalDateTime createdAt = LocalDateTime.now();
    
    private LocalDateTime updatedAt = LocalDateTime.now();
}