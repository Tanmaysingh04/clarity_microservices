package com.clarity.habit_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
public class StreakDTO {
    private int currentStreak;
    private int longestStreak;
    private List<LocalDate> streakDates;
}