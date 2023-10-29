package com.nicolasrodf.habitsapp.home.presentation.home

import com.nicolasrodf.habitsapp.home.domain.models.Habit
import java.time.ZonedDateTime

sealed interface HomeEvent {
    data class ChangeDate(val date: ZonedDateTime) : HomeEvent
    data class CompleteHabit(val habit: Habit) : HomeEvent
}