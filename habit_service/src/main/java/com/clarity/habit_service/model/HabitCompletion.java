package com.clarity.habit_service.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "habit_completions")
@CompoundIndex(name = "user_habit_date", def = "{'userId': 1, 'habitId': 1, 'date': 1}", unique = true)
public class HabitCompletion {
    
    @Id
    private String id;
    
    private String userId;
    
    private String habitId;
    
    private LocalDate date;
    
    private boolean completed;
    
    private LocalDateTime completedAt;
}