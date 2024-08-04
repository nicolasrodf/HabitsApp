package com.nicolasrodf.habitsapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.nicolasrodf.habitsapp.authentication.presentation.login.LoginScreen
import com.nicolasrodf.habitsapp.authentication.presentation.signup.SignupScreen
import com.nicolasrodf.habitsapp.home.presentation.detail.DetailScreen
import com.nicolasrodf.habitsapp.home.presentation.home.HomeScreen
import com.nicolasrf.onboarding_presentation.components.OnboardingScreen

@Composable
fun NavigationHost(
    navHostController: NavHostController,
    startDestination: NavigationRoute,
    logout: () -> Unit
) {
    NavHost(navController = navHostController, startDestination = startDestination.route){
        composable(NavigationRoute.Onboarding.route) {
            com.nicolasrf.onboarding_presentation.components.OnboardingScreen(
                onFinish = {
                    navHostController.popBackStack() //TODO.Fixea el back del Login
                    navHostController.navigate(NavigationRoute.Login.route)
                }
            )
        }
        composable(NavigationRoute.Login.route) {
            LoginScreen(onLogin = {
                navHostController.popBackStack() //TODO.Fixea el back del Login
                navHostController.navigate(NavigationRoute.Home.route)
            }, onSignUp = {
                navHostController.navigate(NavigationRoute.Signup.route)
            })
        }

        composable(NavigationRoute.Signup.route) {
            SignupScreen(onSignIn = {
                navHostController.navigate(NavigationRoute.Home.route) {
                    popUpTo(navHostController.graph.id) {
                        inclusive = true
                    }
                }
            }, onLogin = {
                navHostController.popBackStack()
            })
        }

        composable(NavigationRoute.Home.route) {
            HomeScreen(onNewHabit = {
                navHostController.navigate(NavigationRoute.Detail.route)
            }, onSettings = {
                navHostController.navigate(NavigationRoute.Settings.route)
            }, onEditHabit = {
                navHostController.navigate(NavigationRoute.Detail.route + "?habitId=$it")
            })
        }

        composable(
            NavigationRoute.Detail.route + "?habitId={habitId}",
            arguments = listOf(
                navArgument("habitId") {
                    type = NavType.StringType
                    nullable = true
                    defaultValue = null
                }
            )
        ) {
            DetailScreen(onBack = {
                navHostController.popBackStack()
            }, onSave = {
                navHostController.popBackStack()
            })
        }

        composable(NavigationRoute.Settings.route) {
            com.nicolasrf.settings_presentation.SettingsScreen(
                onBack = {
                    navHostController.popBackStack()
                },
                onLogout = {
                    logout()
                    navHostController.navigate(NavigationRoute.Login.route) {
                        popUpTo(navHostController.graph.id) {
                            inclusive = true
                        }
                    }
                }
            )
        }
    }
}