package com.clarity.task_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskDTO {
    private String id;
    private String title;
    private String description;
    private LocalDate dueDate;
    private String priority;
    private boolean completed;
    private LocalDateTime completedAt;
    private LocalDateTime createdAt;
}