package com.clarity.habit_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HabitDTO {
    private String id;
    private String name;
    private String pillar;
    private List<String> days;
    private String timeOfDay;
    private boolean isCustom;
    private boolean active;
    private LocalDateTime createdAt;
}