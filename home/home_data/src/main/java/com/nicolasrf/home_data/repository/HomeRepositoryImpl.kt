package com.nicolasrf.home_data.repository

import android.util.Log
import androidx.work.BackoffPolicy
import androidx.work.Constraints
import androidx.work.ExistingWorkPolicy
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.nicolasrf.home_data.extension.toStartOfDateTimestamp
import com.nicolasrf.home_data.local.HomeDao
import com.nicolasrf.home_data.mapper.toDomain
import com.nicolasrf.home_data.mapper.toDto
import com.nicolasrf.home_data.mapper.toEntity
import com.nicolasrf.home_data.mapper.toSyncEntity
import com.nicolasrf.home_data.remote.HomeApi
import com.nicolasrf.home_data.remote.util.resultOf
import com.nicolasrf.home_data.sync.HabitSyncWorker
import com.nicolasrf.home_data.utils.Constants.WORKER_MINS_DURATION
import com.nicolasrf.home_domain.alarm.AlarmHandler
import com.nicolasrf.home_domain.models.Habit
import com.nicolasrf.home_domain.repository.HomeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import java.time.Duration
import java.time.ZonedDateTime
import java.util.concurrent.CancellationException

@Suppress("SwallowedException")
class HomeRepositoryImpl(
    private val dao: HomeDao,
    private val api: HomeApi,
    private val alarmHandler: AlarmHandler,
    private val workManager: WorkManager
) : HomeRepository {

    override fun getAllHabitsForSelectedDate(date: ZonedDateTime): Flow<List<Habit>> {
        val localFlow = dao.getAllHabitsForSelectedDate(date.toStartOfDateTimestamp())
            .map { it.map { habitEntity ->  habitEntity.toDomain() } }
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
            Log.d(TAG, "handleAlarm: Habit doesn't exist")
        }
        alarmHandler.setRecurringAlarm(habit)
    }

    override suspend fun getHabitById(id: String): Habit {
        return dao.getHabitById(id).toDomain()
    }

    override suspend fun syncHabits() {
        val worker = OneTimeWorkRequestBuilder<HabitSyncWorker>().setConstraints(
            Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build()
        ).setBackoffCriteria(BackoffPolicy.EXPONENTIAL, Duration.ofMinutes(WORKER_MINS_DURATION))
            .build()

        workManager.beginUniqueWork("sync_habit_id", ExistingWorkPolicy.REPLACE, worker).enqueue()
    }

    companion object {
        private const val TAG = "HomeRepositoryImpl"
    }
}