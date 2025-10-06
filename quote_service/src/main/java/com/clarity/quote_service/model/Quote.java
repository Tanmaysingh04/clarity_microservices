package com.clarity.quote_service.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "quotes")
public class Quote {
    
    @Id
    private String id;
    
    private String text;
    
    private String author;
    
    private String category; // "stoic", "psychology", "productivity", "mindfulness"
    
    private boolean active = true;
}