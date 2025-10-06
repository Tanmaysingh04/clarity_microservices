package com.clarity.habit_service.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "habits")
public class Habit {
    
    @Id
    private String id;
    
    @Indexed
    private String userId;
    
    private String name;
    
    private String pillar; // mental, physical, emotional, productivity, social
    
    private List<String> days; // ["Monday", "Wednesday", "Friday"] or ["Daily"]
    
    private String timeOfDay; // "morning", "day", "evening"
    
    private boolean isCustom; // true if user-created, false if from library
    
    private boolean active = true;
    
    private LocalDateTime createdAt = LocalDateTime.now();
}