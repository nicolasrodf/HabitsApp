package com.nicolasrodf.habitsapp.authentication.domain.usecase

import com.nicolasrodf.habitsapp.authentication.domain.matcher.EmailMatcher

class ValidateEmailUseCase(private val emailMatcher: EmailMatcher) {
    operator fun invoke(email: String): Boolean {
        return emailMatcher.isValid(email)
    }
}