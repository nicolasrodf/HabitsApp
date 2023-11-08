package com.nicolasrodf.habitsapp.home.data.repository

import com.nicolasrodf.habitsapp.home.data.extension.toStartOfDateTimestamp
import com.nicolasrodf.habitsapp.home.data.local.HomeDao
import com.nicolasrodf.habitsapp.home.data.mapper.toDomain
import com.nicolasrodf.habitsapp.home.data.mapper.toEntity
import com.nicolasrodf.habitsapp.home.domain.models.Habit
import com.nicolasrodf.habitsapp.home.domain.repository.HomeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.ZonedDateTime

class HomeRepositoryImpl(
    private val dao: HomeDao
) : HomeRepository {

    override fun getAllHabitsForSelectedDate(date: ZonedDateTime): Flow<List<Habit>> {
        return dao.getAllHabitsForSelectedDate(date.toStartOfDateTimestamp())
            .map { it.map { it.toDomain() } }
    }

    override suspend fun insertHabit(habit: Habit) {
        dao.insertHabit(habit.toEntity())
    }

    override suspend fun getHabitById(id: String): Habit {
        return dao.getHabitById(id).toDomain()
    }
}