package com.hk.habitflow.database

import com.hk.habitflow.database.HabitFlowDatabase
import com.hk.habitflow.domain.model.TaskCategory
import com.hk.habitflow.domain.model.TaskPriority
import com.hk.habitflow.domain.model.TaskWithDetails
import com.hk.habitflow.domain.repository.TaskRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext

class TaskRepositoryImpl(
    private val database: HabitFlowDatabase
) : TaskRepository {

    private val queries = database.taskQueries
    private val categoryQueries = database.taskCategoryQueries
    private val priorityQueries = database.taskPriorityQueries

    override fun getTaskCategories(): Flow<List<TaskCategory>> = flow {
        withContext(databaseDispatcher) {
            emit(categoryQueries.selectAll().executeAsList().map { it.toCategory() })
        }
    }

    override fun getTaskPriorities(): Flow<List<TaskPriority>> = flow {
        withContext(databaseDispatcher) {
            emit(priorityQueries.selectAll().executeAsList().map { it.toPriority() })
        }
    }

    override fun observeTasksByUserId(userId: String): Flow<List<TaskWithDetails>> = flow {
        withContext(databaseDispatcher) {
            emit(queries.selectAllByUserId(userId).executeAsList().map { it.toTaskWithDetails() })
        }
    }

    override suspend fun getTaskById(userId: String, taskId: String): TaskWithDetails? = withContext(databaseDispatcher) {
        queries.selectByUserIdAndId(userId, taskId).executeAsOneOrNull()?.toTaskWithDetails()
    }

    override suspend fun insertTask(task: TaskWithDetails) = withContext(databaseDispatcher) {
        queries.insert(
            id = task.id,
            userId = task.userId,
            title = task.title,
            description = task.description,
            categoryId = task.categoryId,
            priorityId = task.priorityId,
            dueDate = task.dueDate,
            reminderTime = task.reminderTime,
            isCompleted = if (task.isCompleted) 1L else 0L,
            createdAt = task.createdAt,
            completedAt = task.completedAt
        )
    }

    override suspend fun updateTask(task: TaskWithDetails) = withContext(databaseDispatcher) {
        queries.update(
            title = task.title,
            description = task.description,
            categoryId = task.categoryId,
            priorityId = task.priorityId,
            dueDate = task.dueDate,
            reminderTime = task.reminderTime,
            isCompleted = if (task.isCompleted) 1L else 0L,
            completedAt = task.completedAt,
            id = task.id,
            userId = task.userId
        )
    }

    override suspend fun deleteTask(userId: String, taskId: String) = withContext(databaseDispatcher) {
        queries.deleteById(taskId, userId)
    }
}

private fun com.hk.habitflow.database.TaskCategory.toCategory(): TaskCategory = TaskCategory(
    id = id,
    name = name,
    icon = icon
)

private fun com.hk.habitflow.database.TaskPriority.toPriority(): TaskPriority = TaskPriority(
    id = id,
    name = name,
    color = color
)

private fun com.hk.habitflow.database.SelectAllByUserId.toTaskWithDetails(): TaskWithDetails = taskWithDetailsFromRow(id, userId, title, description, categoryId, categoryName, categoryIcon, priorityId, priorityName, priorityColor, dueDate, reminderTime, isCompleted, createdAt, completedAt)

private fun com.hk.habitflow.database.SelectByUserIdAndId.toTaskWithDetails(): TaskWithDetails = taskWithDetailsFromRow(id, userId, title, description, categoryId, categoryName, categoryIcon, priorityId, priorityName, priorityColor, dueDate, reminderTime, isCompleted, createdAt, completedAt)

private fun taskWithDetailsFromRow(
    id: String,
    userId: String,
    title: String,
    description: String?,
    categoryId: String,
    categoryName: String,
    categoryIcon: String?,
    priorityId: String,
    priorityName: String,
    priorityColor: String?,
    dueDate: Long?,
    reminderTime: Long?,
    isCompleted: Long,
    createdAt: Long,
    completedAt: Long?
): TaskWithDetails = TaskWithDetails(
    id = id,
    userId = userId,
    title = title,
    description = description,
    categoryId = categoryId,
    categoryName = categoryName,
    categoryIcon = categoryIcon,
    priorityId = priorityId,
    priorityName = priorityName,
    priorityColor = priorityColor,
    dueDate = dueDate,
    reminderTime = reminderTime,
    isCompleted = isCompleted != 0L,
    createdAt = createdAt,
    completedAt = completedAt
)
