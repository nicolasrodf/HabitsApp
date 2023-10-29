package com.nicolasrodf.habitsapp.home.presentation.home

import com.nicolasrodf.habitsapp.home.domain.models.Habit
import java.time.LocalDate
import java.time.LocalTime
import java.time.ZonedDateTime

data class HomeState(
    val currentDate: ZonedDateTime = ZonedDateTime.now(),
    val selectedDate: ZonedDateTime = ZonedDateTime.now(),
    val habits: List<Habit> = mockHabits
)

private val mockHabits = (1..30).map {
    val dates = mutableListOf<LocalDate>()
    //Los pares fueron completados hoy
    //Los impares fueron completados ayer
    if(it % 2 == 0){
        dates.add(LocalDate.now())
    }else{
        dates.add(LocalDate.now().minusDays(1))
    }
    Habit(
        id = it.toString(),
        name = "Habito $it",
        frequency = listOf(),
        completedDates = dates,
        reminder = LocalTime.now(),
        startDate = ZonedDateTime.now()
    )
}