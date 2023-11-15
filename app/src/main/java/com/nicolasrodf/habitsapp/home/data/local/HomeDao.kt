package com.nicolasrodf.habitsapp.home.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.nicolasrodf.habitsapp.home.data.local.entity.HabitEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface HomeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHabit(habitEntity: HabitEntity)

    @Query("SELECT * FROM HabitEntity WHERE id = :id")
    suspend fun getHabitById(id: String): HabitEntity

    //Recuperar los habitos desde el startDate hasta el selectedDate
    @Query("SELECT * FROM HabitEntity WHERE startDate <= :selectedDate")
    fun getAllHabitsForSelectedDate(selectedDate: Long): Flow<List<HabitEntity>>
}