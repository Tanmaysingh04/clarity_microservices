package com.clarity.analytics_service.controller;

import com.clarity.analytics_service.dto.AnalyticsOverviewDTO;
import com.clarity.analytics_service.dto.ComparisonDTO;
import com.clarity.analytics_service.service.AnalyticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/analytics")
@RequiredArgsConstructor
public class AnalyticsController {
    
    private final AnalyticsService analyticsService;
    
    @GetMapping("/overview")
    public ResponseEntity<?> getOverview(@RequestHeader("X-User-Id") String userId) {
        try {
            AnalyticsOverviewDTO overview = analyticsService.getOverview(userId);
            return ResponseEntity.ok(overview);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.status(500).body(error);
        }
    }
    
    @GetMapping("/comparison")
    public ResponseEntity<?> getComparison(@RequestHeader("X-User-Id") String userId) {
        try {
            ComparisonDTO comparison = analyticsService.getComparison(userId);
            return ResponseEntity.ok(comparison);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.status(500).body(error);
        }
    }
}