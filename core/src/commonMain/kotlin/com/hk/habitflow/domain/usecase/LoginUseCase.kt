package com.hk.habitflow.domain.usecase

import com.hk.habitflow.domain.repository.LoginRepository

class LoginUseCase(
    private val loginRepository: LoginRepository
) {
    suspend operator fun invoke(email: String, password: String): Result<Unit> {
        if (email.isBlank()) {
            return Result.failure(Exception("Email cannot be empty"))
        }
        if (!isValidEmail(email)) {
            return Result.failure(Exception("Invalid email format"))
        }
        if (password.isBlank()) {
            return Result.failure(Exception("Password cannot be empty"))
        }
        if (password.length < 6) {
            return Result.failure(Exception("Password must be at least 6 characters"))
        }

        return loginRepository.login(email, password)
    }

    private fun isValidEmail(email: String): Boolean {
        val emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}\$"
        return emailRegex.toRegex().matches(email)
    }
}
