package com.clarity.reflection_service.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class EveningReflectionRequest {
    
    @NotNull(message = "Date is required")
    private LocalDate date;
    
    @NotNull(message = "Day rating is required")
    @Min(value = 1, message = "Day rating must be between 1 and 10")
    @Max(value = 10, message = "Day rating must be between 1 and 10")
    private Integer dayRating;
    
    private String wentWell;
    
    @NotBlank(message = "Gratitude is required")
    private String gratitude;
    
    @NotNull(message = "Prepared for tomorrow is required")
    private Boolean preparedForTomorrow;
}