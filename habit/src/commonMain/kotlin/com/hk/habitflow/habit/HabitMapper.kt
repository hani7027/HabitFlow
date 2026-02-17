package com.hk.habitflow.habit

import com.hk.habitflow.domain.model.HabitWithDetails
import com.hk.habitflow.habit.model.HabitUi
import com.hk.habitflow.ui.theme.HabitFlowColors

private val iconNameToEmoji = mapOf(
    "dumbbell" to "💪",
    "water" to "💧",
    "book" to "📖",
    "bed" to "🛏",
    "heart" to "❤️",
    "apple" to "🍎",
    "bicycle" to "🚴",
    "coffee" to "☕",
    "music" to "🎵",
    "pencil" to "✏️",
    "moon" to "🌙",
    "plant" to "🌱"
)

private val habitColors = listOf(
    HabitFlowColors.Info,
    HabitFlowColors.Success,
    HabitFlowColors.Primary,
    HabitFlowColors.Focus,
    HabitFlowColors.CategoryOther
)

fun HabitWithDetails.toHabitUi(): HabitUi {
    val emoji = iconNameToEmoji[iconName.lowercase()] ?: "✨"
    val colorIndex = id.hashCode().and(Int.MAX_VALUE) % habitColors.size
    val color = habitColors[colorIndex]
    val targetInt = targetValue.toInt().coerceAtLeast(0)
    return HabitUi(
        id = id,
        name = name,
        description = frequencyTypeDescription.orEmpty(),
        iconEmoji = emoji,
        iconColor = color,
        streakDays = 0,
        isCompletedToday = false,
        progressCurrent = 0,
        progressTarget = if (targetInt > 0) targetInt else null
    )
}
