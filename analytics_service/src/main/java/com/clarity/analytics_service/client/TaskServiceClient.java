package com.clarity.analytics_service.client;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "task-service", path = "/api/tasks")
public interface TaskServiceClient {
    
    @GetMapping("/pending")
    Object getPendingTasks(@RequestHeader("X-User-Id") String userId);
}