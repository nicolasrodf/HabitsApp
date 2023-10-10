package com.nicolasrodf.habitsapp.onboarding.presentation

import androidx.annotation.DrawableRes
import androidx.compose.ui.text.AnnotatedString

data class OnboardingPagerInformation(
    val title: String,
    val subtitle: List<OnboardingSubtitle>,
    @DrawableRes val image: Int
)