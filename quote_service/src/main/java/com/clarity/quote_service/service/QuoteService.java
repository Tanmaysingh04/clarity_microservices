package com.clarity.quote_service.service;

import com.clarity.quote_service.dto.QuoteDTO;
import com.clarity.quote_service.model.Quote;
import com.clarity.quote_service.model.UserQuoteHistory;
import com.clarity.quote_service.repository.QuoteRepository;
import com.clarity.quote_service.repository.UserQuoteHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class QuoteService {
    
    private final QuoteRepository quoteRepository;
    private final UserQuoteHistoryRepository historyRepository;
    
    public QuoteDTO getDailyQuote(String userId) {
        List<Quote> allQuotes = quoteRepository.findByActiveTrue();
        
        if (allQuotes.isEmpty()) {
            throw new RuntimeException("No quotes available");
        }
        
        List<UserQuoteHistory> userHistory = historyRepository.findByUserIdOrderByShownAtDesc(userId);
        
        Set<String> recentlyShownIds = userHistory.stream()
                .limit(10)
                .map(UserQuoteHistory::getQuoteId)
                .collect(Collectors.toSet());
        
        List<Quote> availableQuotes = allQuotes.stream()
                .filter(q -> !recentlyShownIds.contains(q.getId()))
                .collect(Collectors.toList());
        
        if (availableQuotes.isEmpty()) {
            availableQuotes = allQuotes;
        }
        
        Quote selectedQuote = availableQuotes.get(new Random().nextInt(availableQuotes.size()));
        
        recordQuoteShown(userId, selectedQuote.getId());
        
        return convertToDTO(selectedQuote);
    }
    
    public List<QuoteDTO> getMoreQuotes(String userId, int count) {
        List<Quote> allQuotes = quoteRepository.findByActiveTrue();
        
        if (allQuotes.isEmpty()) {
            return new ArrayList<>();
        }
        
        List<UserQuoteHistory> userHistory = historyRepository.findByUserIdOrderByShownAtDesc(userId);
        
        Set<String> recentlyShownIds = userHistory.stream()
                .limit(10)
                .map(UserQuoteHistory::getQuoteId)
                .collect(Collectors.toSet());
        
        List<Quote> availableQuotes = allQuotes.stream()
                .filter(q -> !recentlyShownIds.contains(q.getId()))
                .collect(Collectors.toList());
        
        if (availableQuotes.isEmpty()) {
            availableQuotes = allQuotes;
        }
        
        Collections.shuffle(availableQuotes);
        
        List<Quote> selectedQuotes = availableQuotes.stream()
                .limit(count)
                .collect(Collectors.toList());
        
        selectedQuotes.forEach(q -> recordQuoteShown(userId, q.getId()));
        
        return selectedQuotes.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    private void recordQuoteShown(String userId, String quoteId) {
        Optional<UserQuoteHistory> existing = historyRepository.findByUserIdAndQuoteId(userId, quoteId);
        
        if (existing.isPresent()) {
            UserQuoteHistory history = existing.get();
            history.setShownCount(history.getShownCount() + 1);
            history.setShownAt(LocalDateTime.now());
            historyRepository.save(history);
        } else {
            UserQuoteHistory history = new UserQuoteHistory();
            history.setUserId(userId);
            history.setQuoteId(quoteId);
            history.setShownAt(LocalDateTime.now());
            history.setShownCount(1);
            historyRepository.save(history);
        }
    }
    
    private QuoteDTO convertToDTO(Quote quote) {
        return new QuoteDTO(
                quote.getId(),
                quote.getText(),
                quote.getAuthor(),
                quote.getCategory()
        );
    }
}