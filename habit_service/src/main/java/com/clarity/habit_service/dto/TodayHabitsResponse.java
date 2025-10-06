package com.clarity.habit_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class TodayHabitsResponse {
    private List<HabitDTO> morning;
    private List<HabitDTO> day;
    private List<HabitDTO> evening;
}