package com.clarity.quote_service.repository;

import com.clarity.quote_service.model.Quote;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuoteRepository extends MongoRepository<Quote, String> {
    
    List<Quote> findByActiveTrue();
    
    long countByActiveTrue();
}