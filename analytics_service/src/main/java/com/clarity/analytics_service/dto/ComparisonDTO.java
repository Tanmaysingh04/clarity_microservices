package com.clarity.analytics_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ComparisonDTO {
    private WeekStats thisWeek;
    private WeekStats lastWeek;
    private ChangeStats change;
    
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class WeekStats {
        private int habitsCompleted;
        private int tasksCompleted;
    }
    
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ChangeStats {
        private int habitsDifference;
        private int tasksDifference;
        private String direction; // "up" or "down"
    }
}