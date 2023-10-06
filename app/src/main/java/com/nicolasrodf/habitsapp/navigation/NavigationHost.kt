package com.nicolasrodf.habitsapp.navigation

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.nicolasrodf.habitsapp.onboarding.OnboardingScreen

@Composable
fun NavigationHost(
    navHostController: NavHostController,
    startDestination: NavigationRoute
) {
    NavHost(navController = navHostController, startDestination = startDestination.route){
        when(startDestination){
            NavigationRoute.OnBoarding -> {
                composable(startDestination.route){
                    OnboardingScreen()
                }
            }
        }
    }
}