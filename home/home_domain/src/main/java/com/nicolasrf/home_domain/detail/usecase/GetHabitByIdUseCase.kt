package com.nicolasrf.home_domain.detail.usecase

import com.nicolasrf.home_domain.models.Habit
import com.nicolasrf.home_domain.repository.HomeRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GetHabitByIdUseCase(
    private val repository: HomeRepository
) {
    suspend operator fun invoke(id: String): Habit {
        return withContext(Dispatchers.IO) {
            repository.getHabitById(id)
        }
    }
}