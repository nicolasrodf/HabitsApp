package com.nicolasrodf.habitsapp.onboarding

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun OnboardingScreen() {
    val pages = listOf(
        "Titulo 1",
        "Titulo 2",
        "Titulo 3"
    )
    OnboardingPager(pages = pages, onFinish = { /**/ })
}