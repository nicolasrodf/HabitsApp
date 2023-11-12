package com.nicolasrodf.habitsapp.home.domain.models

import java.time.DayOfWeek
import java.time.LocalDate
import java.time.LocalTime
import java.time.ZonedDateTime

data class Habit(
    val id: String, //autogenerado
    val name: String, //editable
    val frequency: List<DayOfWeek>, //editable
    val completedDates: List<LocalDate>, //editable en el Home
    val reminder: LocalTime,//editable
    val startDate: ZonedDateTime //no editable, el dia en que es creado el Habito
)