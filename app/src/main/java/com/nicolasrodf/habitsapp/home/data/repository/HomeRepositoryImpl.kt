package com.nicolasrodf.habitsapp.home.data.repository

import com.nicolasrodf.habitsapp.home.domain.models.Habit
import com.nicolasrodf.habitsapp.home.domain.repository.HomeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.LocalTime
import java.time.ZonedDateTime

class HomeRepositoryImpl : HomeRepository {
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
            frequency = listOf(DayOfWeek.THURSDAY), //for test
            completedDates = dates,
            reminder = LocalTime.now(),
            startDate = ZonedDateTime.now()
        )
    }.toMutableList()

    override fun getAllHabitsForSelectedDate(date: ZonedDateTime): Flow<List<Habit>> {
        return flowOf(mockHabits.toList())
    }

    override suspend fun insertHabit(habit: Habit) {
        val index = mockHabits.indexOfFirst { it.id == habit.id }
        //SI EL HABIT NO EXISTE
        if (index == -1) {
            mockHabits.add(habit)
        } else {
            mockHabits.removeAt(index)
            mockHabits.add(index, habit)
        }
    }

    override suspend fun getHabitById(id: String): Habit {
        return mockHabits.first { it.id == id }
    }
}