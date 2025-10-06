package com.clarity.task_service.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class UpdateTaskRequest {
    private String title;
    private String description;
    private LocalDate dueDate;
    private String priority;
}