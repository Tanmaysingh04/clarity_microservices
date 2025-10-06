package com.clarity.habit_service.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "streaks")
public class Streak {
    
    @Id
    private String id;
    
    @Indexed(unique = true)
    private String userId;
    
    private int currentStreak = 0;
    
    private int longestStreak = 0;
    
    private LocalDate lastCompletionDate;
    
    private List<LocalDate> streakDates = new ArrayList<>();
}