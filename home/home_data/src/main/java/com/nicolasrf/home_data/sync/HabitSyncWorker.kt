package com.nicolasrf.home_data.sync

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.nicolasrf.home_data.local.HomeDao
import com.nicolasrf.home_data.local.entity.HabitSyncEntity
import com.nicolasrf.home_data.mapper.toDomain
import com.nicolasrf.home_data.mapper.toDto
import com.nicolasrf.home_data.remote.HomeApi
import com.nicolasrf.home_data.remote.util.resultOf
import com.nicolasrf.home_data.utils.Constants.MAX_WORKER_SYNCS_ATTEMPTS
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.supervisorScope

@Suppress("SwallowedException")
@HiltWorker
class HabitSyncWorker @AssistedInject constructor(
    @Assisted val context: Context,
    @Assisted val workerParameters: WorkerParameters,
    private val api: HomeApi,
    private val dao: HomeDao
) : CoroutineWorker(context, workerParameters) {
    /*
    * Lo que hace el codigo por dentro es recorrer todos los habitos por subir a la Api
    * y correr cada llamada a la Api en su propia corutina de forma paralela.
    * En caso la Api falle, se invocara el Result.retry() el cual aumenta +1 el runAttemptCount
    * Si se han hecho mas de 3 intentos, se lanza el Result.failure() y esto cancela el proceso completo
    * de sincronizado
    * Ojo: este Result es de Workmanager
    * */
    override suspend fun doWork(): Result {
        if (runAttemptCount >= MAX_WORKER_SYNCS_ATTEMPTS) {
            return Result.failure()
        }

        val items = dao.getAllHabitsSync()

        return try {
            supervisorScope {
                val jobs = items.map { items -> async { sync(items) } }
                jobs.awaitAll()
            }
            Result.success()
        } catch (e: Exception) {
            Result.retry()
        }
    }

    private suspend fun sync(entity: HabitSyncEntity) {
        val habit = dao.getHabitById(entity.id).toDomain().toDto()
        resultOf {
            api.insertHabit(habit)
        }.onSuccess {
            dao.deleteHabitSync(entity)
        }.onFailure {
            throw it
        }
    }
}