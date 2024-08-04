package com.nicolasrf.onboarding_domain.usecase

import com.nicolasrf.onboarding_domain.repository.OnboardingRepository

class CompleteOnboardingUseCase(
    private val repository: OnboardingRepository
) {
    operator fun invoke() {
        repository.completeOnboarding()
    }
}