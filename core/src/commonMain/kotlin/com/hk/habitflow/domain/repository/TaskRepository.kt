package com.hk.habitflow.domain.repository

import com.hk.habitflow.domain.model.TaskCategory
import com.hk.habitflow.domain.model.TaskPriority
import com.hk.habitflow.domain.model.TaskWithDetails
import kotlinx.coroutines.flow.Flow

/**
 * User-scoped task operations. All queries filter by userId.
 */
interface TaskRepository {
    fun getTaskCategories(): Flow<List<TaskCategory>>
    fun getTaskPriorities(): Flow<List<TaskPriority>>
    fun observeTasksByUserId(userId: String): Flow<List<TaskWithDetails>>
    suspend fun getTaskById(userId: String, taskId: String): TaskWithDetails?
    suspend fun insertTask(task: TaskWithDetails)
    suspend fun updateTask(task: TaskWithDetails)
    suspend fun deleteTask(userId: String, taskId: String)
}
