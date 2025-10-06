package com.clarity.habit_service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.List;

@Data
public class CreateHabitRequest {
    
    @NotBlank(message = "Habit name is required")
    private String name;
    
    @NotBlank(message = "Pillar is required")
    private String pillar;
    
    @NotEmpty(message = "Days are required")
    private List<String> days;
    
    @NotBlank(message = "Time of day is required")
    private String timeOfDay; // morning, day, evening
    
    private boolean isCustom = true;
}