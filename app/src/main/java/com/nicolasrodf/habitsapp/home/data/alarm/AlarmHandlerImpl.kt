package com.nicolasrodf.habitsapp.home.data.alarm

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import com.nicolasrodf.habitsapp.home.data.extension.toTimeStamp
import com.nicolasrodf.habitsapp.home.domain.alarm.AlarmHandler
import com.nicolasrodf.habitsapp.home.domain.models.Habit
import java.time.DayOfWeek
import java.time.ZonedDateTime

class AlarmHandlerImpl(
    private val context: Context
) : AlarmHandler {
    private val alarmManager = context.getSystemService(AlarmManager::class.java)

    /*
     * setea la alarma en el dia y hora seleccionada
     */
    override fun setRecurringAlarm(habit: Habit) {
        val nextOccurrence = calculateNextOccurrence(habit)
        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            nextOccurrence.toTimeStamp(),
            createPendingIntent(habit, nextOccurrence.dayOfWeek)
        )
    }

    private fun createPendingIntent(habit: Habit, dayOfWeek: DayOfWeek): PendingIntent {
        val intent = Intent(context, AlarmReceiver::class.java).apply {
            putExtra(AlarmReceiver.HABIT_ID, habit.id)
        }
        return PendingIntent.getBroadcast(
            context,
            habit.id.hashCode() * 10 + dayOfWeek.value, //id unico y frecuencia unica
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
    }

    /*
    * Calcula si la alarma se seteara el dia de hoy o cualquiera de los siguientes dias que este
    * en la frecuencia
    */
    private fun calculateNextOccurrence(habit: Habit): ZonedDateTime {
        //Ej frequency L-J-S
        val today = ZonedDateTime.now() //Ej Sabado 11 nov 23:00
        var nextOccurrence = ZonedDateTime.of(today.toLocalDate(), habit.reminder, today.zone) //Ej Sabado 11 nov 16:00
        //Si en el Ej la hora de hoy hubiese sido antes de las 16:00 (reminder), devolveria
        //el nextOccurrence como Sabado 11 nov 16:00.
        //Pero para el Ej, entraria al do-while
        if (habit.frequency.contains(today.dayOfWeek) && today.isBefore(nextOccurrence)) {
            return nextOccurrence
        }

        //El do-while recorrera todos los dias a partir de hoy y si el dia esta
        //en la frecuencia , el nextOcurrence sera ese dia a esa hora
        //Para el Ej, seria para el Lunes 13 nov 16:00
        do {
            nextOccurrence = nextOccurrence.plusDays(1)
        } while (!habit.frequency.contains(nextOccurrence.dayOfWeek))

        return nextOccurrence
    }

    /*
    * Cancela la siguiente alarma creada para el Habito
    * */
    override fun cancel(habit: Habit) {
        val nextOccurrence = calculateNextOccurrence(habit)
        val pending = createPendingIntent(habit, nextOccurrence.dayOfWeek)
        alarmManager.cancel(pending)
    }
}
