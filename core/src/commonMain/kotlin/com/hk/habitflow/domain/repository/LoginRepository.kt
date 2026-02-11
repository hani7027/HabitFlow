package com.hk.habitflow.domain.repository

interface LoginRepository {
    suspend fun login(email: String, password: String): Result<Unit>
}
