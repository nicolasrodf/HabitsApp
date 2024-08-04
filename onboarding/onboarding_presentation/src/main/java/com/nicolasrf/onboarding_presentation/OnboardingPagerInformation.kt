package com.nicolasrf.onboarding_presentation

import androidx.annotation.DrawableRes

data class OnboardingPagerInformation(
    val title: String,
    val subtitle: List<OnboardingSubtitle>,
    @DrawableRes val image: Int
)