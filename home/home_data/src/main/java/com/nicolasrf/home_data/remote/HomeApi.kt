package com.nicolasrf.home_data.remote

import com.nicolasrf.home_data.remote.dto.HabitResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH

interface HomeApi {
    companion object {
        const val BASE_URL = "https://vercosearchapp.firebaseio.com/"
    }

    @GET("habits.json")
    suspend fun getAllHabits(): HabitResponse

    @PATCH("habits.json")
    suspend fun insertHabit(@Body habit: HabitResponse)
}