package com.nicolasrodf.habitsapp.home.domain.detail.usecase

import com.nicolasrodf.habitsapp.home.domain.models.Habit
import com.nicolasrodf.habitsapp.home.domain.repository.HomeRepository

class InsertHabitUseCase(private val repository: HomeRepository) {
    suspend operator fun invoke(habit: Habit) {
        repository.insertHabit(habit)
    }
}