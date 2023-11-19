package com.nicolasrodf.habitsapp.authentication.domain.usecase

import com.nicolasrodf.habitsapp.authentication.domain.repository.AuthenticationRepository

class LogoutUseCase(private val repository: AuthenticationRepository) {
    suspend operator fun invoke() {
        return repository.logout()
    }
}