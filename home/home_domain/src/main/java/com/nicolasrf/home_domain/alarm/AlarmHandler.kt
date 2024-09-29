package com.nicolasrf.home_domain.alarm

import com.nicolasrf.home_domain.models.Habit

interface AlarmHandler {
    fun setRecurringAlarm(habit: Habit)
    fun cancel(habit: Habit)
}