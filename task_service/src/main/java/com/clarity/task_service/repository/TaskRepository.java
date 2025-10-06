package com.clarity.task_service.repository;

import com.clarity.task_service.model.Task;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TaskRepository extends MongoRepository<Task, String> {
    
    List<Task> findByUserId(String userId);
    
    List<Task> findByUserIdAndCompletedFalse(String userId);
    
    List<Task> findByUserIdAndDueDate(String userId, LocalDate dueDate);
    
    List<Task> findByUserIdAndDueDateBetween(String userId, LocalDate startDate, LocalDate endDate);
}