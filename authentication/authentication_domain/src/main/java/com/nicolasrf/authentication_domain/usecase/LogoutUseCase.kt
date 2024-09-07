package com.nicolasrf.authentication_domain.usecase

import com.nicolasrf.authentication_domain.repository.AuthenticationRepository

class LogoutUseCase(private val repository: AuthenticationRepository) {
    suspend operator fun invoke() {
        return repository.logout()
    }
}