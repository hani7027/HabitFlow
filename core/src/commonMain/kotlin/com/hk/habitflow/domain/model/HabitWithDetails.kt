package com.hk.habitflow.domain.model

/**
 * Habit with joined icon and frequency type (for listing and editing).
 */
data class HabitWithDetails(
    val id: String,
    val userId: String,
    val name: String,
    val iconId: String,
    val iconName: String,
    val frequencyTypeId: String,
    val frequencyTypeName: String,
    val frequencyTypeDescription: String?,
    val targetValue: Long,
    val reminderTime: Long?,
    val createdAt: Long,
    val isArchived: Boolean
)
