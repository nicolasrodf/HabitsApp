package com.nicolasrodf.habitsapp.utils

import com.nicolasrodf.habitsapp.R
import com.nicolasrodf.habitsapp.onboarding.presentation.OnboardingPagerInformation
import com.nicolasrodf.habitsapp.onboarding.presentation.OnboardingSubtitle
import com.nicolasrodf.habitsapp.ui.theme.Accent
import com.nicolasrodf.habitsapp.ui.theme.Yellow1

object Utils {

    val subtitleData = listOf(
        OnboardingSubtitle("We can ", Accent),
        OnboardingSubtitle("help you ", Yellow1),
        OnboardingSubtitle("to be a better \n version of ", Accent),
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