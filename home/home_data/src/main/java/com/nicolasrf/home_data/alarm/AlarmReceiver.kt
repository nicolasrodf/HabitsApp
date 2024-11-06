package com.nicolasrf.home_data.alarm

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import com.nicolasrf.home_data.R
import com.nicolasrf.home_data.extension.goAsync
import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalDate
import javax.inject.Inject

@AndroidEntryPoint
class AlarmReceiver : BroadcastReceiver() {
    companion object {
        const val HABIT_ID = "habit_id"
        private const val CHANNEL_ID = "habits_channel"
    }

    @Inject
    lateinit var repository: com.nicolasrf.home_domain.repository.HomeRepository

    @Inject
    lateinit var alarmHandler: com.nicolasrf.home_domain.alarm.AlarmHandler

    override fun onReceive(context: Context?, intent: Intent?) = goAsync {
        if (context == null || intent == null) return@goAsync
        val id = intent.getStringExtra(HABIT_ID) ?: return@goAsync
        val habit = repository.getHabitById(id)
        createNotificationChannel(context)
        //Si el habito no ha sido completado el dia de hoy, mostrar la notificacion
        if (!habit.completedDates.contains(LocalDate.now())) {
            showNotification(context, habit)
        }
        alarmHandler.setRecurringAlarm(habit)
    }

    private fun showNotification(context: Context, habit: com.nicolasrf.home_domain.models.Habit) {
        val notificationManager = context.getSystemService(NotificationManager::class.java)
        val notification = NotificationCompat.Builder(context, CHANNEL_ID)
            .setContentTitle(habit.name)
            .setSmallIcon(R.drawable.ic_notification)
            .setAutoCancel(true)
            .build()
        notificationManager.notify(habit.id.hashCode(), notification)
        //hashCode del id para que las notificaciones sean unicas y no se pisen una tras otra
    }

    private fun createNotificationChannel(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                "Habit Reminder Channel",
                NotificationManager.IMPORTANCE_HIGH
            )
            channel.description = "Get your habits reminder!"
            val notificationManager = context.getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(channel)
        }
    }
}
