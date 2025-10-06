package com.clarity.analytics_service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "habit-service", path = "/api/habits")
public interface HabitServiceClient {
    
    @GetMapping("/streak")
    Object getStreak(@RequestHeader("X-User-Id") String userId);
}