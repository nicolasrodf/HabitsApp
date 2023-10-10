package com.nicolasrodf.habitsapp.onboarding

import androidx.compose.runtime.Composable
import com.nicolasrodf.habitsapp.R
import com.nicolasrodf.habitsapp.onboarding.presentation.OnboardingPagerInformation
import com.nicolasrodf.habitsapp.utils.Utils

@Composable
fun OnboardingScreen(
    onFinish: () -> Unit
) {
    val pages = listOf(
        OnboardingPagerInformation(
            title = "Welcome to \n Monumental Habits",
            subtitle = Utils.subtitleData,
            image = R.drawable.onboarding1
        ),
        OnboardingPagerInformation(
            title = "Create new \n habits easily",
            subtitle = Utils.subtitleData,
            image = R.drawable.onboarding2
        ),
        OnboardingPagerInformation(
            title = "Keep track of your \n progress",
            subtitle = Utils.subtitleData,
            image = R.drawable.onboarding3
        ),
        OnboardingPagerInformation(
            title = "Join a supportive \n community",
            subtitle = Utils.subtitleData,
            image = R.drawable.onboarding4
        )
    )

    OnboardingPager(pages = pages, onFinish = onFinish)
}