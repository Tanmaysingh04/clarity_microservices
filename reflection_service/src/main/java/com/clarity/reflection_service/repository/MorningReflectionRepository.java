package com.clarity.reflection_service.repository;

import com.clarity.reflection_service.model.MorningReflection;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface MorningReflectionRepository extends MongoRepository<MorningReflection, String> {
    
    Optional<MorningReflection> findByUserIdAndDate(String userId, LocalDate date);
    
    List<MorningReflection> findByUserIdOrderByDateDesc(String userId);
}