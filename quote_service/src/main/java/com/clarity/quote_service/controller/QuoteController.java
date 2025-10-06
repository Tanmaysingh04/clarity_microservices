package com.clarity.quote_service.controller;

import com.clarity.quote_service.dto.QuoteDTO;
import com.clarity.quote_service.service.QuoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/quotes")
@RequiredArgsConstructor
public class QuoteController {
    
    private final QuoteService quoteService;
    
    @GetMapping("/daily")
    public ResponseEntity<?> getDailyQuote(@RequestHeader("X-User-Id") String userId) {
        try {
            QuoteDTO quote = quoteService.getDailyQuote(userId);
            return ResponseEntity.ok(quote);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.status(500).body(error);
        }
    }
    
    @GetMapping("/more")
    public ResponseEntity<?> getMoreQuotes(
            @RequestHeader("X-User-Id") String userId,
            @RequestParam(defaultValue = "3") int count) {
        try {
            List<QuoteDTO> quotes = quoteService.getMoreQuotes(userId, count);
            return ResponseEntity.ok(quotes);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.status(500).body(error);
        }
    }
}