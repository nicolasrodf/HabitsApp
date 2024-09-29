package com.nicolasrodf.habitsapp.home

import android.util.Log
import androidx.activity.compose.setContent
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.test.platform.app.InstrumentationRegistry
import androidx.work.Configuration
import androidx.work.testing.SynchronousExecutor
import androidx.work.testing.WorkManagerTestInitHelper
import com.nicolasrodf.habitsapp.MainActivity
import com.nicolasrodf.habitsapp.home.data.repository.FakeHomeRepository
import com.nicolasrf.home_domain.detail.usecase.DetailUseCases
import com.nicolasrf.home_domain.detail.usecase.GetHabitByIdUseCase
import com.nicolasrf.home_domain.detail.usecase.InsertHabitUseCase
import com.nicolasrf.home_domain.home.usecase.CompleteHabitUseCase
import com.nicolasrf.home_domain.home.usecase.GetHabitsForDateUseCase
import com.nicolasrf.home_domain.home.usecase.HomeUseCases
import com.nicolasrf.home_domain.home.usecase.SyncHabitUseCase
import com.nicolasrf.home_presentation.detail.DetailScreen
import com.nicolasrf.home_presentation.detail.DetailViewModel
import com.nicolasrf.home_presentation.home.HomeScreen
import com.nicolasrf.home_presentation.home.HomeViewModel
import com.nicolasrodf.habitsapp.navigation.NavigationRoute
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.time.LocalDate

@HiltAndroidTest
class CreateHabitE2E {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @get:Rule
    val composeRule = createAndroidComposeRule<MainActivity>()

    private lateinit var homeRepository: FakeHomeRepository
    private lateinit var homeViewModel: com.nicolasrf.home_presentation.home.HomeViewModel
    private lateinit var detailViewModel: com.nicolasrf.home_presentation.detail.DetailViewModel
    private lateinit var navController: NavHostController

    @Before
    fun setup() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        val config = Configuration.Builder().setMinimumLoggingLevel(Log.DEBUG)
            .setExecutor(SynchronousExecutor())
            .build()
        WorkManagerTestInitHelper.initializeTestWorkManager(context, config)
        homeRepository = FakeHomeRepository()
        val usecases = com.nicolasrf.home_domain.home.usecase.HomeUseCases(
            completeHabitUseCase = com.nicolasrf.home_domain.home.usecase.CompleteHabitUseCase(
                homeRepository
            ),
            getHabitsForDateUseCase = com.nicolasrf.home_domain.home.usecase.GetHabitsForDateUseCase(
                homeRepository
            ),
            syncHabitUseCase = com.nicolasrf.home_domain.home.usecase.SyncHabitUseCase(
                homeRepository
            )
        )
        homeViewModel = com.nicolasrf.home_presentation.home.HomeViewModel(usecases)

        val detailUseCase = com.nicolasrf.home_domain.detail.usecase.DetailUseCases(
            getHabitByIdUseCase = com.nicolasrf.home_domain.detail.usecase.GetHabitByIdUseCase(
                homeRepository
            ),
            insertHabitUseCase = com.nicolasrf.home_domain.detail.usecase.InsertHabitUseCase(
                homeRepository
            )
        )
        detailViewModel = com.nicolasrf.home_presentation.detail.DetailViewModel(
            SavedStateHandle(),
            detailUseCase
        )

        composeRule.activity.setContent {
            navController = rememberNavController()
            NavHost(navController = navController, startDestination = NavigationRoute.Home.route) {
                composable(NavigationRoute.Home.route) {
                    com.nicolasrf.home_presentation.home.HomeScreen(
                        onNewHabit = {
                            navController.navigate(NavigationRoute.Detail.route)
                        },
                        onSettings = {
                            navController.navigate(NavigationRoute.Settings.route)
                        },
                        onEditHabit = {
                            navController.navigate(NavigationRoute.Detail.route + "?habitId=$it")
                        },
                        viewModel = homeViewModel
                    )
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
                    com.nicolasrf.home_presentation.detail.DetailScreen(
                        onBack = {
                            navController.popBackStack()
                        },
                        onSave = {
                            navController.popBackStack()
                        },
                        viewModel = detailViewModel
                    )
                }
            }
        }
    }

    @Test
    fun createHabit() {
        val habitToCreate = "Vamos al Gym"
        composeRule.onNodeWithText("Home").assertIsDisplayed()
        composeRule.onNodeWithText(habitToCreate).assertDoesNotExist()
        composeRule.onNodeWithContentDescription("Add a new habit").performClick()
        assert(navController.currentDestination?.route?.startsWith(NavigationRoute.Detail.route) == true)
        composeRule.onNodeWithText("New Habit").assertIsDisplayed()
        composeRule.onNodeWithContentDescription("Enter habit name").performClick()
            .performTextInput(habitToCreate)
        val today = LocalDate.now().dayOfWeek
        composeRule.onNodeWithContentDescription(today.name).performClick()
        composeRule.onNodeWithContentDescription("Enter habit name").performImeAction()
        composeRule.onNodeWithText("Home").assertIsDisplayed()
        assert(navController.currentDestination?.route?.startsWith(NavigationRoute.Home.route) == true)
        composeRule.onNodeWithText(habitToCreate).assertIsDisplayed()
    }
}