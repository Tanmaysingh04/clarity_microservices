package com.clarity.quote_service.repository;

import com.clarity.quote_service.model.UserQuoteHistory;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserQuoteHistoryRepository extends MongoRepository<UserQuoteHistory, String> {
    
    Optional<UserQuoteHistory> findByUserIdAndQuoteId(String userId, String quoteId);
    
    List<UserQuoteHistory> findByUserIdOrderByShownAtDesc(String userId);
}