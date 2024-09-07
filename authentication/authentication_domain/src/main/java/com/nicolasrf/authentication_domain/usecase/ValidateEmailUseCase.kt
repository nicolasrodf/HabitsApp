package com.nicolasrf.authentication_domain.usecase

import com.nicolasrf.authentication_domain.matcher.EmailMatcher

class ValidateEmailUseCase(private val emailMatcher: EmailMatcher) {
    operator fun invoke(email: String): Boolean {
        return emailMatcher.isValid(email)
    }
}