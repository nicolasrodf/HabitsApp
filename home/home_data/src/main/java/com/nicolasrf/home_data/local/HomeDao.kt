package com.nicolasrf.home_data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.nicolasrf.home_data.local.entity.HabitEntity
import com.nicolasrf.home_data.local.entity.HabitSyncEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface HomeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHabit(habitEntity: HabitEntity)

    @Query("SELECT * FROM HabitEntity WHERE id = :id")
    suspend fun getHabitById(id: String): HabitEntity

    //Recuperar los habitos desde el startDate hasta el selectedDate
    @Query("SELECT * FROM HabitEntity WHERE startDate <= :selectedDate ORDER BY id ASC")
    fun getAllHabitsForSelectedDate(selectedDate: Long): Flow<List<HabitEntity>>

    @Query("SELECT * FROM HabitEntity")
    fun getAllHabits(): List<HabitEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHabitSync(habitSyncEntity: HabitSyncEntity)

    @Query("SELECT * FROM HabitSyncEntity")
    fun getAllHabitsSync(): List<HabitSyncEntity>

    @Delete
    suspend fun deleteHabitSync(habitSyncEntity: HabitSyncEntity)
}