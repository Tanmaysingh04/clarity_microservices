package com.clarity.habit_service.service;

import com.clarity.habit_service.dto.*;
import com.clarity.habit_service.model.Habit;
import com.clarity.habit_service.model.HabitCompletion;
import com.clarity.habit_service.model.Streak;
import com.clarity.habit_service.repository.HabitCompletionRepository;
import com.clarity.habit_service.repository.HabitRepository;
import com.clarity.habit_service.repository.StreakRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HabitService {
    
    private final HabitRepository habitRepository;
    private final HabitCompletionRepository completionRepository;
    private final StreakRepository streakRepository;
    
    public String createHabit(String userId, CreateHabitRequest request) {
        Habit habit = new Habit();
        habit.setUserId(userId);
        habit.setName(request.getName());
        habit.setPillar(request.getPillar());
        habit.setDays(request.getDays());
        habit.setTimeOfDay(request.getTimeOfDay());
        habit.setCustom(request.isCustom());
        habit.setActive(true);
        habit.setCreatedAt(LocalDateTime.now());
        
        Habit savedHabit = habitRepository.save(habit);
        return savedHabit.getId();
    }
    
    public List<HabitDTO> getAllHabits(String userId) {
        List<Habit> habits = habitRepository.findByUserIdAndActiveTrue(userId);
        return habits.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    public TodayHabitsResponse getTodayHabits(String userId, LocalDate date) {
        List<Habit> allHabits = habitRepository.findByUserIdAndActiveTrue(userId);
        
        String dayOfWeek = date.getDayOfWeek().toString();
        final String todayName = dayOfWeek.substring(0, 1) + dayOfWeek.substring(1).toLowerCase();
        
        List<Habit> todayHabits = allHabits.stream()
                .filter(h -> h.getDays().contains("Daily") || h.getDays().contains(todayName))
                .collect(Collectors.toList());
        
        List<HabitDTO> morning = todayHabits.stream()
                .filter(h -> "morning".equals(h.getTimeOfDay()))
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        
        List<HabitDTO> day = todayHabits.stream()
                .filter(h -> "day".equals(h.getTimeOfDay()))
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        
        List<HabitDTO> evening = todayHabits.stream()
                .filter(h -> "evening".equals(h.getTimeOfDay()))
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        
        return new TodayHabitsResponse(morning, day, evening);
    }
    
    public void updateHabit(String userId, String habitId, CreateHabitRequest request) {
        Habit habit = habitRepository.findById(habitId)
                .orElseThrow(() -> new RuntimeException("Habit not found"));
        
        if (!habit.getUserId().equals(userId)) {
            throw new RuntimeException("Unauthorized");
        }
        
        habit.setName(request.getName());
        habit.setPillar(request.getPillar());
        habit.setDays(request.getDays());
        habit.setTimeOfDay(request.getTimeOfDay());
        
        habitRepository.save(habit);
    }
    
    public void deleteHabit(String userId, String habitId) {
        Habit habit = habitRepository.findById(habitId)
                .orElseThrow(() -> new RuntimeException("Habit not found"));
        
        if (!habit.getUserId().equals(userId)) {
            throw new RuntimeException("Unauthorized");
        }
        
        habit.setActive(false);
        habitRepository.save(habit);
    }
    
    public void completeHabit(String userId, String habitId, CompleteHabitRequest request) {
        HabitCompletion completion = completionRepository
                .findByUserIdAndHabitIdAndDate(userId, habitId, request.getDate())
                .orElse(new HabitCompletion());
        
        completion.setUserId(userId);
        completion.setHabitId(habitId);
        completion.setDate(request.getDate());
        completion.setCompleted(request.getCompleted());
        
        if (request.getCompleted()) {
            completion.setCompletedAt(LocalDateTime.now());
        }
        
        completionRepository.save(completion);
        
        updateStreak(userId, request.getDate());
    }
    
    public StreakDTO getStreak(String userId) {
        Streak streak = streakRepository.findByUserId(userId)
                .orElse(new Streak(null, userId, 0, 0, null, new ArrayList<>()));
        
        return new StreakDTO(
                streak.getCurrentStreak(),
                streak.getLongestStreak(),
                streak.getStreakDates()
        );
    }
    
    private void updateStreak(String userId, LocalDate date) {
        Streak streak = streakRepository.findByUserId(userId)
                .orElse(new Streak(null, userId, 0, 0, null, new ArrayList<>()));
        
        long completedToday = completionRepository.countByUserIdAndDateAndCompletedTrue(userId, date);
        
        if (completedToday > 0) {
            if (!streak.getStreakDates().contains(date)) {
                streak.getStreakDates().add(date);
            }
            
            if (streak.getLastCompletionDate() == null || 
                date.isAfter(streak.getLastCompletionDate())) {
                
                if (streak.getLastCompletionDate() != null && 
                    date.minusDays(1).equals(streak.getLastCompletionDate())) {
                    streak.setCurrentStreak(streak.getCurrentStreak() + 1);
                } else if (streak.getLastCompletionDate() == null || 
                           !date.equals(streak.getLastCompletionDate())) {
                    streak.setCurrentStreak(1);
                }
                
                streak.setLastCompletionDate(date);
                
                if (streak.getCurrentStreak() > streak.getLongestStreak()) {
                    streak.setLongestStreak(streak.getCurrentStreak());
                }
            }
        }
        
        streakRepository.save(streak);
    }
    
    private HabitDTO convertToDTO(Habit habit) {
        return new HabitDTO(
                habit.getId(),
                habit.getName(),
                habit.getPillar(),
                habit.getDays(),
                habit.getTimeOfDay(),
                habit.isCustom(),
                habit.isActive(),
                habit.getCreatedAt()
        );
    }
}