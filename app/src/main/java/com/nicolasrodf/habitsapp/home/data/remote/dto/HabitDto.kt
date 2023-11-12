package com.nicolasrodf.habitsapp.home.data.remote.dto

//Un DTO representa lo que enviamos a la Api
data class HabitDto(
    //el ID esta en el Map, ya que Firebase pone el id como cabecera del objeto
    //Las variables tienen que estar ordenadas como estan en Firebase
    val name: String,
    val reminder: Long,
    val startDate: Long,
    val frequency: List<Int>,
    val completedDates: List<Long>?
)