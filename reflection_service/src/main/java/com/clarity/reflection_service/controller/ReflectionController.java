package com.clarity.reflection_service.controller;

import com.clarity.reflection_service.dto.*;
import com.clarity.reflection_service.model.EveningReflection;
import com.clarity.reflection_service.model.MorningReflection;
import com.clarity.reflection_service.service.ReflectionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/reflections")
@RequiredArgsConstructor
public class ReflectionController {
    
    private final ReflectionService reflectionService;
    
    @PostMapping("/morning")
    public ResponseEntity<?> saveMorningReflection(
            @RequestHeader("X-User-Id") String userId,
            @Valid @RequestBody MorningReflectionRequest request) {
        try {
            reflectionService.saveMorningReflection(userId, request);
            Map<String, String> response = new HashMap<>();
            response.put("message", "Morning reflection saved successfully");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }
    
    @GetMapping("/morning/{date}")
    public ResponseEntity<?> getMorningReflection(
            @RequestHeader("X-User-Id") String userId,
            @PathVariable String date) {
        try {
            MorningReflection reflection = reflectionService.getMorningReflection(userId, date);
            if (reflection == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", "Reflection not found"));
            }
            return ResponseEntity.ok(reflection);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }
    
    @PostMapping("/evening")
    public ResponseEntity<?> saveEveningReflection(
            @RequestHeader("X-User-Id") String userId,
            @Valid @RequestBody EveningReflectionRequest request) {
        try {
            reflectionService.saveEveningReflection(userId, request);
            Map<String, String> response = new HashMap<>();
            response.put("message", "Evening reflection saved successfully");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }
    
    @GetMapping("/evening/{date}")
    public ResponseEntity<?> getEveningReflection(
            @RequestHeader("X-User-Id") String userId,
            @PathVariable String date) {
        try {
            EveningReflection reflection = reflectionService.getEveningReflection(userId, date);
            if (reflection == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", "Reflection not found"));
            }
            return ResponseEntity.ok(reflection);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }
    
    @PostMapping("/journal")
    public ResponseEntity<?> createJournal(
            @RequestHeader("X-User-Id") String userId,
            @Valid @RequestBody JournalRequest request) {
        try {
            String journalId = reflectionService.createJournal(userId, request);
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Journal created successfully");
            response.put("journalId", journalId);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }
    
    @GetMapping("/journals")
    public ResponseEntity<?> getAllJournals(
            @RequestHeader("X-User-Id") String userId,
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer limit) {
        try {
            List<JournalDTO> journals = reflectionService.getAllJournals(userId, page, limit);
            return ResponseEntity.ok(journals);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }
    
    @GetMapping("/journal/{journalId}")
    public ResponseEntity<?> getJournal(
            @RequestHeader("X-User-Id") String userId,
            @PathVariable String journalId) {
        try {
            JournalDTO journal = reflectionService.getJournalById(userId, journalId);
            return ResponseEntity.ok(journal);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }
    }
    
    @PutMapping("/journal/{journalId}")
    public ResponseEntity<?> updateJournal(
            @RequestHeader("X-User-Id") String userId,
            @PathVariable String journalId,
            @Valid @RequestBody JournalRequest request) {
        try {
            reflectionService.updateJournal(userId, journalId, request);
            Map<String, String> response = new HashMap<>();
            response.put("message", "Journal updated successfully");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }
    
    @DeleteMapping("/journal/{journalId}")
    public ResponseEntity<?> deleteJournal(
            @RequestHeader("X-User-Id") String userId,
            @PathVariable String journalId) {
        try {
            reflectionService.deleteJournal(userId, journalId);
            Map<String, String> response = new HashMap<>();
            response.put("message", "Journal deleted successfully");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }
}