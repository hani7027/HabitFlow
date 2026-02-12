package com.hk.habitflow.habit.model

import androidx.compose.ui.graphics.Color

/**
 * UI model for a habit on the Habits screen.
 * For countable habits (e.g. glasses of water), use progressCurrent/progressTarget.
 * For simple daily habits, use isCompletedToday only (progressTarget = null).
 */
data class HabitUi(
    val id: String,
    val name: String,
    val description: String,
    val iconEmoji: String,
    val iconColor: Color,
    val streakDays: Int,
    val isCompletedToday: Boolean,
    val progressCurrent: Int = 0,
    val progressTarget: Int? = null
) {
    val isCountable: Boolean get() = progressTarget != null && progressTarget > 0
    val progressFraction: Float
        get() = when (val target = progressTarget) {
            null, 0 -> if (isCompletedToday) 1f else 0f
            else -> (progressCurrent.toFloat() / target).coerceIn(0f, 1f)
        }
}
