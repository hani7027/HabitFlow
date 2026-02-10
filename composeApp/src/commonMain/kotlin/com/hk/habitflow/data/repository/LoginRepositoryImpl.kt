package com.hk.habitflow.data.repository

import com.hk.habitflow.domain.repository.LoginRepository
import kotlinx.coroutines.delay

class LoginRepositoryImpl : LoginRepository {
    override suspend fun login(email: String, password: String): Result<Unit> {
        delay(1500)
        
        if (email == "test@example.com" && password == "password123") {
            return Result.success(Unit)
        }
        
        return Result.failure(Exception("Invalid email or password"))
    }
}
