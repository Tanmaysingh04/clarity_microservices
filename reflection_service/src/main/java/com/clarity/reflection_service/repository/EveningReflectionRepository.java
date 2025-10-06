package com.clarity.reflection_service.repository;

import com.clarity.reflection_service.model.EveningReflection;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface EveningReflectionRepository extends MongoRepository<EveningReflection, String> {
    
    Optional<EveningReflection> findByUserIdAndDate(String userId, LocalDate date);
    
    List<EveningReflection> findByUserIdOrderByDateDesc(String userId);
}