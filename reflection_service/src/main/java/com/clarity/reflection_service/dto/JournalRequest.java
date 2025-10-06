package com.clarity.reflection_service.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class JournalRequest {
    
    private String title;
    
    @NotBlank(message = "Content is required")
    private String content;
}