package com.clarity.reflection_service.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class MorningReflectionRequest {
    
    @NotNull(message = "Date is required")
    private LocalDate date;
    
    @NotNull(message = "Sleep rating is required")
    @Min(value = 1, message = "Sleep rating must be between 1 and 10")
    @Max(value = 10, message = "Sleep rating must be between 1 and 10")
    private Integer sleepRating;
    
    private List<String> focusTags;
    
    private String intention;
}