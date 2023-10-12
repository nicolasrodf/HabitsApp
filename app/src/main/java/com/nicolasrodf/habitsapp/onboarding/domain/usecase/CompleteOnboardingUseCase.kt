package com.nicolasrodf.habitsapp.onboarding.domain.usecase

import com.nicolasrodf.habitsapp.onboarding.domain.repository.OnboardingRepository

class CompleteOnboardingUseCase(
    private val repository: OnboardingRepository
) {
    operator fun invoke() {
        repository.completeOnboarding()
    }
}