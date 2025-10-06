package com.clarity.quote_service.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "user_quote_history")
@CompoundIndex(name = "user_quote", def = "{'userId': 1, 'quoteId': 1}")
public class UserQuoteHistory {
    
    @Id
    private String id;
    
    private String userId;
    
    private String quoteId;
    
    private LocalDateTime shownAt = LocalDateTime.now();
    
    private int shownCount = 1;
}