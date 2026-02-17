package com.hk.habitflow.domain.repository

import com.hk.habitflow.domain.model.User

/**
 * User authentication: register (store hashed password), login (retrieve by email for verification).
 */
interface UserRepository {
    suspend fun getByEmail(email: String): User?
    suspend fun getById(id: String): User?
    suspend fun insert(user: User)
}
