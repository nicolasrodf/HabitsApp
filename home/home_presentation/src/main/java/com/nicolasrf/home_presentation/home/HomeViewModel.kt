package com.nicolasrf.home_presentation.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nicolasrf.home_domain.home.usecase.HomeUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val homeUseCases: HomeUseCases
) : ViewModel() {
    var state by mutableStateOf(HomeState())
        private set
    private var currentDayJob: Job? = null

    init {
        getHabits()
        viewModelScope.launch {
            homeUseCases.syncHabitUseCase()
        }
    }

    fun onEvent(event: HomeEvent) {
        when (event) {
            is HomeEvent.ChangeDate -> {
                state = state.copy(
                    selectedDate = event.date
                )
                getHabits()
            }
            is HomeEvent.CompleteHabit -> {
                viewModelScope.launch {
                    homeUseCases.completeHabitUseCase(event.habit, state.selectedDate)
                }
            }
        }
    }

    private fun getHabits() {
        //Cancelamos la corutina anterior para que cada vez que se cambie la fecha, no se este
        //creando una nueva corutina. Tambien se podria poner el estado de la fecha como un Flow
        //y observar cambio de fecha con una sola corutina.
        currentDayJob?.cancel()
        currentDayJob = viewModelScope.launch {
            homeUseCases.getHabitsForDateUseCase(state.selectedDate).collectLatest {
                state = state.copy(
                    habits = it
                )
            }
        }
    }

}