package com.clarity.reflection_service.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "morning_reflections")
@CompoundIndex(name = "user_date", def = "{'userId': 1, 'date': 1}", unique = true)
public class MorningReflection {
    
    @Id
    private String id;
    
    private String userId;
    
    private LocalDate date;
    
    private Integer sleepRating; // 1-10
    
    private List<String> focusTags; // ["Work", "Family", "Self-care"]
    
    private String intention; // Optional one-line
    
    private LocalDateTime completedAt = LocalDateTime.now();
}