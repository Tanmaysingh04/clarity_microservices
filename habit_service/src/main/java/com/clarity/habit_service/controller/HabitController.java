package com.clarity.habit_service.controller;

import com.clarity.habit_service.dto.*;
import com.clarity.habit_service.service.HabitService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/habits")
@RequiredArgsConstructor
public class HabitController {
    
    private final HabitService habitService;
    
    @PostMapping
    public ResponseEntity<?> createHabit(
            @RequestHeader("X-User-Id") String userId,
            @Valid @RequestBody CreateHabitRequest request) {
        try {
            String habitId = habitService.createHabit(userId, request);
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Habit created successfully");
            response.put("habitId", habitId);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }
    
    @GetMapping
    public ResponseEntity<?> getAllHabits(@RequestHeader("X-User-Id") String userId) {
        try {
            List<HabitDTO> habits = habitService.getAllHabits(userId);
            return ResponseEntity.ok(habits);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }
    
    @GetMapping("/today")
    public ResponseEntity<?> getTodayHabits(
            @RequestHeader("X-User-Id") String userId,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        try {
            LocalDate targetDate = date != null ? date : LocalDate.now();
            TodayHabitsResponse response = habitService.getTodayHabits(userId, targetDate);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }
    
    @PutMapping("/{habitId}")
    public ResponseEntity<?> updateHabit(
            @RequestHeader("X-User-Id") String userId,
            @PathVariable String habitId,
            @Valid @RequestBody CreateHabitRequest request) {
        try {
            habitService.updateHabit(userId, habitId, request);
            Map<String, String> response = new HashMap<>();
            response.put("message", "Habit updated successfully");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }
    
    @DeleteMapping("/{habitId}")
    public ResponseEntity<?> deleteHabit(
            @RequestHeader("X-User-Id") String userId,
            @PathVariable String habitId) {
        try {
            habitService.deleteHabit(userId, habitId);
            Map<String, String> response = new HashMap<>();
            response.put("message", "Habit deleted successfully");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }
    
    @PostMapping("/{habitId}/complete")
    public ResponseEntity<?> completeHabit(
            @RequestHeader("X-User-Id") String userId,
            @PathVariable String habitId,
            @Valid @RequestBody CompleteHabitRequest request) {
        try {
            habitService.completeHabit(userId, habitId, request);
            Map<String, String> response = new HashMap<>();
            response.put("message", "Habit completion recorded");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }
    
    @GetMapping("/streak")
    public ResponseEntity<?> getStreak(@RequestHeader("X-User-Id") String userId) {
        try {
            StreakDTO streak = habitService.getStreak(userId);
            return ResponseEntity.ok(streak);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }
}