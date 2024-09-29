package com.nicolasrf.home_domain.home.usecase

import com.nicolasrf.home_domain.repository.HomeRepository

class SyncHabitUseCase(private val repository: HomeRepository) {
    suspend operator fun invoke() {
        repository.syncHabits()
    }
}