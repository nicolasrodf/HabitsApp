package com.nicolasrodf.habitsapp.navigation

sealed class NavigationRoute(val route: String){
    object OnBoarding : NavigationRoute("onboarding")
}
