package com.nicolasrodf.habitsapp.home.domain.home.usecase

import com.nicolasrodf.habitsapp.home.domain.repository.HomeRepository

class SyncHabitUseCase(private val repository: HomeRepository) {
    suspend operator fun invoke() {
        repository.syncHabits()
    }
}