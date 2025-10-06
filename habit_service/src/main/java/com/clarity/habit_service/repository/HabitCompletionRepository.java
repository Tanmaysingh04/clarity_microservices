package com.clarity.habit_service.repository;

import com.clarity.habit_service.model.HabitCompletion;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface HabitCompletionRepository extends MongoRepository<HabitCompletion, String> {
    
    Optional<HabitCompletion> findByUserIdAndHabitIdAndDate(String userId, String habitId, LocalDate date);
    
    List<HabitCompletion> findByUserIdAndDateBetween(String userId, LocalDate startDate, LocalDate endDate);
    
    List<HabitCompletion> findByUserIdAndDate(String userId, LocalDate date);
    
    long countByUserIdAndDateAndCompletedTrue(String userId, LocalDate date);
}