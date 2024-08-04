package com.nicolasrf.onboarding_presentation.components

import com.nicolasrf.core_presentation.colors.Accent
import com.nicolasrf.core_presentation.colors.Yellow1
import com.nicolasrf.onboarding_presentation.R

object Utils {

    val subtitleData = listOf(
        com.nicolasrf.onboarding_presentation.OnboardingSubtitle("We can ", Accent),
        com.nicolasrf.onboarding_presentation.OnboardingSubtitle("help you ", Yellow1),
        com.nicolasrf.onboarding_presentation.OnboardingSubtitle(
            "to be a better \n version of ",
            Accent
        ),
        com.nicolasrf.onboarding_presentation.OnboardingSubtitle("yourself.", Yellow1),
    )

    val onboardingPages = listOf(
        com.nicolasrf.onboarding_presentation.OnboardingPagerInformation(
            title = "Welcome to \n Monumental Habits",
            subtitle = subtitleData,
            image = R.drawable.onboarding1
        ),
        com.nicolasrf.onboarding_presentation.OnboardingPagerInformation(
            title = "Create new habits easily",
            subtitle = subtitleData,
            image = R.drawable.onboarding2
        ),
        com.nicolasrf.onboarding_presentation.OnboardingPagerInformation(
            title = "Keep track of your progress",
            subtitle = subtitleData,
            image = R.drawable.onboarding3
        ),
        com.nicolasrf.onboarding_presentation.OnboardingPagerInformation(
            title = "Join a supportive community",
            subtitle = subtitleData,
            image = R.drawable.onboarding4
        )
    )

}