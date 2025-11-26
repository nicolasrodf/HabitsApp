package com.nicolasrf.onboarding_presentation.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.nicolasrf.core_presentation.colors.Accent
import com.nicolasrf.core_presentation.colors.Yellow1
import com.nicolasrf.onboarding_presentation.OnboardingPagerInformation
import com.nicolasrf.onboarding_presentation.OnboardingSubtitle
import com.nicolasrf.onboarding_presentation.OnboardingViewModel
import com.nicolasrf.onboarding_presentation.R

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

    val subtitleData = listOf(
        OnboardingSubtitle(stringResource(id = R.string.onboarding_subtitle_we_can), Accent),
        OnboardingSubtitle(stringResource(id = R.string.onboarding_subtitle_help_you), Yellow1),
        OnboardingSubtitle(
            stringResource(id = R.string.onboarding_subtitle_better_version),
            Accent
        ),
        OnboardingSubtitle(stringResource(id = R.string.onboarding_subtitle_yourself), Yellow1),
    )

    val pages = listOf(
        OnboardingPagerInformation(
            title = stringResource(id = R.string.welcome_title),
            subtitle = subtitleData,
            image = R.drawable.onboarding1
        ),
        OnboardingPagerInformation(
            title = stringResource(id = R.string.onboarding_title_2),
            subtitle = subtitleData,
            image = R.drawable.onboarding2
        ),
        OnboardingPagerInformation(
            title = stringResource(id = R.string.onboarding_title_3),
            subtitle = subtitleData,
            image = R.drawable.onboarding3
        ),
        OnboardingPagerInformation(
            title = stringResource(id = R.string.onboarding_title_4),
            subtitle = subtitleData,
            image = R.drawable.onboarding4
        )
    )

    OnboardingPager(pages = pages, onFinish = {
        viewModel.completeOnboarding()
    })
}