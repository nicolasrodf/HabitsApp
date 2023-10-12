package com.nicolasrodf.habitsapp.onboarding

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import com.nicolasrodf.habitsapp.R
import com.nicolasrodf.habitsapp.onboarding.presentation.OnboardingPagerInformation
import com.nicolasrodf.habitsapp.onboarding.presentation.OnboardingViewModel
import com.nicolasrodf.habitsapp.utils.Utils

@Composable
fun OnboardingScreen(
    onFinish: () -> Unit,
    viewModel: OnboardingViewModel = hiltViewModel(),
) {

    //se invoca la 1ra vez que se carga el composable y cuuando el key o los keys cambien
    LaunchedEffect(key1 = viewModel.hasSeenOnboarding) {
        if (viewModel.hasSeenOnboarding) {
            onFinish()
        }
    }

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

    OnboardingPager(pages = pages, onFinish = {
        viewModel.completeOnboarding()
    })
}