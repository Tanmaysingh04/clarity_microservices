package com.clarity.reflection_service.model;

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
@Document(collection = "evening_reflections")
@CompoundIndex(name = "user_date", def = "{'userId': 1, 'date': 1}", unique = true)
public class EveningReflection {
    
    @Id
    private String id;
    
    private String userId;
    
    private LocalDate date;
    
    private Integer dayRating; // 1-10
    
    private String wentWell; // Optional text
    
    private String gratitude;
    
    private Boolean preparedForTomorrow; // Yes/No
    
    private LocalDateTime completedAt = LocalDateTime.now();
}