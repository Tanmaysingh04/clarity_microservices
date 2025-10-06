package com.clarity.reflection_service.service;

import com.clarity.reflection_service.dto.*;
import com.clarity.reflection_service.model.EveningReflection;
import com.clarity.reflection_service.model.Journal;
import com.clarity.reflection_service.model.MorningReflection;
import com.clarity.reflection_service.repository.EveningReflectionRepository;
import com.clarity.reflection_service.repository.JournalRepository;
import com.clarity.reflection_service.repository.MorningReflectionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReflectionService {
    
    private final MorningReflectionRepository morningRepository;
    private final EveningReflectionRepository eveningRepository;
    private final JournalRepository journalRepository;
    
    public void saveMorningReflection(String userId, MorningReflectionRequest request) {
        MorningReflection reflection = morningRepository
                .findByUserIdAndDate(userId, request.getDate())
                .orElse(new MorningReflection());
        
        reflection.setUserId(userId);
        reflection.setDate(request.getDate());
        reflection.setSleepRating(request.getSleepRating());
        reflection.setFocusTags(request.getFocusTags());
        reflection.setIntention(request.getIntention());
        reflection.setCompletedAt(LocalDateTime.now());
        
        morningRepository.save(reflection);
    }
    
    public MorningReflection getMorningReflection(String userId, String date) {
        return morningRepository.findByUserIdAndDate(userId, java.time.LocalDate.parse(date))
                .orElse(null);
    }
    
    public void saveEveningReflection(String userId, EveningReflectionRequest request) {
        EveningReflection reflection = eveningRepository
                .findByUserIdAndDate(userId, request.getDate())
                .orElse(new EveningReflection());
        
        reflection.setUserId(userId);
        reflection.setDate(request.getDate());
        reflection.setDayRating(request.getDayRating());
        reflection.setWentWell(request.getWentWell());
        reflection.setGratitude(request.getGratitude());
        reflection.setPreparedForTomorrow(request.getPreparedForTomorrow());
        reflection.setCompletedAt(LocalDateTime.now());
        
        eveningRepository.save(reflection);
    }
    
    public EveningReflection getEveningReflection(String userId, String date) {
        return eveningRepository.findByUserIdAndDate(userId, java.time.LocalDate.parse(date))
                .orElse(null);
    }
    
    public String createJournal(String userId, JournalRequest request) {
        Journal journal = new Journal();
        journal.setUserId(userId);
        journal.setTitle(request.getTitle());
        journal.setContent(request.getContent());
        journal.setCreatedAt(LocalDateTime.now());
        journal.setUpdatedAt(LocalDateTime.now());
        
        Journal saved = journalRepository.save(journal);
        return saved.getId();
    }
    
    public List<JournalDTO> getAllJournals(String userId, Integer page, Integer limit) {
        Pageable pageable = PageRequest.of(
                page != null ? page : 0, 
                limit != null ? limit : 10,
                Sort.by(Sort.Direction.DESC, "createdAt")
        );
        
        return journalRepository.findByUserId(userId, pageable)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    public JournalDTO getJournalById(String userId, String journalId) {
        Journal journal = journalRepository.findById(journalId)
                .orElseThrow(() -> new RuntimeException("Journal not found"));
        
        if (!journal.getUserId().equals(userId)) {
            throw new RuntimeException("Unauthorized");
        }
        
        return convertToDTO(journal);
    }
    
    public void updateJournal(String userId, String journalId, JournalRequest request) {
        Journal journal = journalRepository.findById(journalId)
                .orElseThrow(() -> new RuntimeException("Journal not found"));
        
        if (!journal.getUserId().equals(userId)) {
            throw new RuntimeException("Unauthorized");
        }
        
        journal.setTitle(request.getTitle());
        journal.setContent(request.getContent());
        journal.setUpdatedAt(LocalDateTime.now());
        
        journalRepository.save(journal);
    }
    
    public void deleteJournal(String userId, String journalId) {
        Journal journal = journalRepository.findById(journalId)
                .orElseThrow(() -> new RuntimeException("Journal not found"));
        
        if (!journal.getUserId().equals(userId)) {
            throw new RuntimeException("Unauthorized");
        }
        
        journalRepository.delete(journal);
    }
    
    private JournalDTO convertToDTO(Journal journal) {
        return new JournalDTO(
                journal.getId(),
                journal.getTitle(),
                journal.getContent(),
                journal.getCreatedAt(),
                journal.getUpdatedAt()
        );
    }
}