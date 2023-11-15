package com.nicolasrodf.habitsapp.home.domain.alarm

import com.nicolasrodf.habitsapp.home.domain.models.Habit

interface AlarmHandler {
    fun setRecurringAlarm(habit: Habit)
    fun cancel(habit: Habit)
}