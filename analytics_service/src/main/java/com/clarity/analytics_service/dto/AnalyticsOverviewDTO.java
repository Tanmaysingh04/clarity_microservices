package com.clarity.analytics_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AnalyticsOverviewDTO {
    private int currentStreak;
    private int pendingTasksCount;
    private String message;
}