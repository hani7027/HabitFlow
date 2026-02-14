package com.hk.habitflow.database

import com.hk.habitflow.database.HabitFlowDatabase
import com.hk.habitflow.domain.model.User
import com.hk.habitflow.domain.repository.UserRepository
import kotlinx.coroutines.withContext

class UserRepositoryImpl(
    private val database: HabitFlowDatabase
) : UserRepository {

    private val queries = database.userQueries

    override suspend fun getByEmail(email: String): User? = withContext(databaseDispatcher) {
        queries.selectByEmail(email).executeAsOneOrNull()?.toDomain()
    }

    override suspend fun getById(id: String): User? = withContext(databaseDispatcher) {
        queries.selectById(id).executeAsOneOrNull()?.toDomain()
    }

    override suspend fun insert(user: User) = withContext(databaseDispatcher) {
        queries.insert(
            id = user.id,
            name = user.name,
            email = user.email,
            passwordHash = user.passwordHash,
            createdAt = user.createdAt
        )
    }
}

private fun com.hk.habitflow.database.User.toDomain(): com.hk.habitflow.domain.model.User = com.hk.habitflow.domain.model.User(
    id = id,
    name = name,
    email = email,
    passwordHash = passwordHash,
    createdAt = createdAt
)
