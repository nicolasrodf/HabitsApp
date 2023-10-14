package com.nicolasrodf.habitsapp.authentication.domain.repository
interface AuthenticationRepository {
    suspend fun login(email: String, password: String): Result<Unit>
}