package com.clarity.quote_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuoteDTO {
    private String id;
    private String text;
    private String author;
    private String category;
}