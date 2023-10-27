package com.nicolasrodf.habitsapp.authentication.domain.usecase

import com.nicolasrodf.habitsapp.authentication.domain.repository.AuthenticationRepository

class GetUserIdUseCase(private val repository: AuthenticationRepository) {
    operator fun invoke(): String? {
        return repository.getUserId()
    }
}