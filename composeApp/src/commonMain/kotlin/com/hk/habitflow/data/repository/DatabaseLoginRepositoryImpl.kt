package com.hk.habitflow.data.repository

import com.hk.habitflow.domain.repository.LoginRepository
import com.hk.habitflow.domain.repository.UserRepository
import com.hk.habitflow.session.SessionHolder

/**
 * Login with a single fixed credential for now. Set session on success.
 * Replace with UserRepository/DB check when adding real auth.
 */
class DatabaseLoginRepositoryImpl(
    private val userRepository: UserRepository
) : LoginRepository {

    private val fixedEmail = "admin@habitflow.com"
    private val fixedPassword = "admin123"
    private val fixedUserId = "user_1"

    override suspend fun login(email: String, password: String): Result<Unit> {
        if (email.trim().equals(fixedEmail, ignoreCase = true) && password == fixedPassword) {
            SessionHolder.userId = fixedUserId
            return Result.success(Unit)
        }
        return Result.failure(Exception("Invalid email or password"))
    }
}
