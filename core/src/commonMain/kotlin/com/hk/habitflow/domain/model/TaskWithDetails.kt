package com.hk.habitflow.domain.model

/**
 * Task with joined category and priority (for listing and editing).
 */
data class TaskWithDetails(
    val id: String,
    val userId: String,
    val title: String,
    val description: String?,
    val categoryId: String,
    val categoryName: String,
    val categoryIcon: String?,
    val priorityId: String,
    val priorityName: String,
    val priorityColor: String?,
    val dueDate: Long?,
    val reminderTime: Long?,
    val isCompleted: Boolean,
    val createdAt: Long,
    val completedAt: Long?
)
