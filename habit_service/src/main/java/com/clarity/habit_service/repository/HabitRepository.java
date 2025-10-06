package com.clarity.habit_service.repository;

import com.clarity.habit_service.model.Habit;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HabitRepository extends MongoRepository<Habit, String> {
    
    List<Habit> findByUserIdAndActiveTrue(String userId);
    
    List<Habit> findByUserId(String userId);
}