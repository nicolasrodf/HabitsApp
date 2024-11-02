package com.nicolasrf.onboarding_presentation.components

import com.nicolasrf.core_presentation.colors.Accent
import com.nicolasrf.core_presentation.colors.Yellow1
import com.nicolasrf.onboarding_presentation.OnboardingPagerInformation
import com.nicolasrf.onboarding_presentation.OnboardingSubtitle
import com.nicolasrf.onboarding_presentation.R

object Utils {

    val subtitleData = listOf(
        OnboardingSubtitle("We can ", Accent),
        OnboardingSubtitle("help you ", Yellow1),
        OnboardingSubtitle(
            "to be a better \n version of ",
            Accent
        ),
        OnboardingSubtitle("yourself.", Yellow1),
    )

    val onboardingPages = listOf(
        OnboardingPagerInformation(
            title = "Welcome to \n Monumental Habits",
            subtitle = subtitleData,
            image = R.drawable.onboarding1
        ),
        OnboardingPagerInformation(
            title = "Create new habits easily",
            subtitle = subtitleData,
            image = R.drawable.onboarding2
        ),
        OnboardingPagerInformation(
            title = "Keep track of your progress",
            subtitle = subtitleData,
            image = R.drawable.onboarding3
        ),
        OnboardingPagerInformation(
            title = "Join a supportive community",
            subtitle = subtitleData,
            image = R.drawable.onboarding4
        )
    )

}