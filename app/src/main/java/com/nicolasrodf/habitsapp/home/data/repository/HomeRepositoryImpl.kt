package com.nicolasrodf.habitsapp.home.data.repository

import androidx.work.*
import com.nicolasrodf.habitsapp.home.data.extension.toStartOfDateTimestamp
import com.nicolasrodf.habitsapp.home.data.local.HomeDao
import com.nicolasrodf.habitsapp.home.data.mapper.toDomain
import com.nicolasrodf.habitsapp.home.data.mapper.toDto
import com.nicolasrodf.habitsapp.home.data.mapper.toEntity
import com.nicolasrodf.habitsapp.home.data.mapper.toSyncEntity
import com.nicolasrodf.habitsapp.home.data.remote.HomeApi
import com.nicolasrodf.habitsapp.home.data.remote.util.resultOf
import com.nicolasrodf.habitsapp.home.data.sync.HabitSyncWorker
import com.nicolasrodf.habitsapp.home.domain.alarm.AlarmHandler
import com.nicolasrodf.habitsapp.home.domain.models.Habit
import com.nicolasrodf.habitsapp.home.domain.repository.HomeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import java.time.Duration
import java.time.ZonedDateTime
import java.util.concurrent.CancellationException

class HomeRepositoryImpl(
    private val dao: HomeDao,
    private val api: HomeApi,
    private val alarmHandler: AlarmHandler,
    private val workManager: WorkManager
) : HomeRepository {

    override fun getAllHabitsForSelectedDate(date: ZonedDateTime): Flow<List<Habit>> {
        val localFlow = dao.getAllHabitsForSelectedDate(date.toStartOfDateTimestamp())
            .map { it.map { it.toDomain() } }
        val apiFlow = getHabitsFromApi()

        //'combine' suma 2 flows y emite los cambios de ambos combinados
        return localFlow.combine(apiFlow) { db, _ ->
            db
        }
    }

    private fun getHabitsFromApi(): Flow<List<Habit>> {
        return flow {
            resultOf {
                val habits = api.getAllHabits().toDomain()
                insertHabits(habits)
            }
            try {
                val habits = api.getAllHabits().toDomain()
                insertHabits(habits)
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                e.printStackTrace()
            }
            emit(emptyList<Habit>())
        }.onStart {
            //cuando comience el flow necesitamos emitir una lista vacia para que el combine
            //emita los valores inmediatamente, si la API se demora en responder
            //los emitira cuando estes disponibles
            emit(emptyList())
        }
    }

    override suspend fun insertHabit(habit: Habit) {
        handleAlarm(habit)
        dao.insertHabit(habit.toEntity())
        resultOf {
            api.insertHabit(habit.toDto())
        }.onFailure {
            dao.insertHabitSync(habit.toSyncEntity())
        }
    }

    private suspend fun insertHabits(habits: List<Habit>) {
        habits.forEach {
            handleAlarm(it)
            dao.insertHabit(it.toEntity())
        }
    }

    private suspend fun handleAlarm(habit: Habit) {
        try {
            val previous = dao.getHabitById(habit.id)
            alarmHandler.cancel(previous.toDomain())
        } catch (e: Exception) { /* Habit doesn't exist */
        }
        alarmHandler.setRecurringAlarm(habit)
    }

    override suspend fun getHabitById(id: String): Habit {
        return dao.getHabitById(id).toDomain()
    }

    override suspend fun syncHabits() {
        val worker = OneTimeWorkRequestBuilder<HabitSyncWorker>().setConstraints(
            Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build()
        ).setBackoffCriteria(BackoffPolicy.EXPONENTIAL, Duration.ofMinutes(5))
            .build()

        workManager.beginUniqueWork("sync_habit_id", ExistingWorkPolicy.REPLACE, worker).enqueue()
    }
}