package com.clarity.analytics_service.service;

import com.clarity.analytics_service.client.HabitServiceClient;
import com.clarity.analytics_service.client.TaskServiceClient;
import com.clarity.analytics_service.dto.AnalyticsOverviewDTO;
import com.clarity.analytics_service.dto.ComparisonDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AnalyticsService {
    
    private final HabitServiceClient habitServiceClient;
    private final TaskServiceClient taskServiceClient;
    
    public AnalyticsOverviewDTO getOverview(String userId) {
        try {
            Map<String, Object> streakData = (Map<String, Object>) habitServiceClient.getStreak(userId);
            int currentStreak = (Integer) streakData.getOrDefault("currentStreak", 0);
            
            List<?> pendingTasks = (List<?>) taskServiceClient.getPendingTasks(userId);
            int pendingCount = pendingTasks.size();
            
            return new AnalyticsOverviewDTO(
                currentStreak,
                pendingCount,
                "Analytics overview retrieved successfully"
            );
        } catch (Exception e) {
            return new AnalyticsOverviewDTO(0, 0, "Error fetching analytics: " + e.getMessage());
        }
    }
    
    public ComparisonDTO getComparison(String userId) {
        ComparisonDTO.WeekStats thisWeek = new ComparisonDTO.WeekStats(0, 0);
        ComparisonDTO.WeekStats lastWeek = new ComparisonDTO.WeekStats(0, 0);
        ComparisonDTO.ChangeStats change = new ComparisonDTO.ChangeStats(0, 0, "neutral");
        
        return new ComparisonDTO(thisWeek, lastWeek, change);
    }
}