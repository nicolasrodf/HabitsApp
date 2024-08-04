package com.nicolasrf.onboarding_domain.usecase

import com.nicolasrf.onboarding_domain.repository.OnboardingRepository

class HasSeenOnboardingUseCase(
    private val repository: OnboardingRepository
) {
    operator fun invoke(): Boolean {
        return repository.hasSeenOnboarding()
    }
}