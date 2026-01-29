package com.nicolasrf.authentication_domain.usecase

import com.nicolasrf.authentication_domain.repository.AuthenticationRepository

class ResetPasswordUseCase(
    private val repository: AuthenticationRepository
) {
    suspend operator fun invoke(email: String): Result<Unit> {
        return repository.resetPassword(email)
    }
}
