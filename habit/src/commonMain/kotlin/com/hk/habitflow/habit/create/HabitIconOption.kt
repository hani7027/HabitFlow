package com.hk.habitflow.habit.create

import androidx.compose.ui.graphics.Color
import com.hk.habitflow.ui.theme.HabitFlowColors

data class HabitIconOption(val emoji: String, val color: Color)

val defaultHabitIcons: List<HabitIconOption> = listOf(
    HabitIconOption("💪", HabitFlowColors.Info),
    HabitIconOption("💧", HabitFlowColors.Success),
    HabitIconOption("📖", HabitFlowColors.Primary),
    HabitIconOption("🛏", HabitFlowColors.Focus),
    HabitIconOption("🧘", HabitFlowColors.CategoryOther),
    HabitIconOption("🍎", HabitFlowColors.PriorityHigh),
    HabitIconOption("🚴", HabitFlowColors.Info),
    HabitIconOption("☕", HabitFlowColors.Focus),
    HabitIconOption("🎵", HabitFlowColors.Primary),
    HabitIconOption("✏️", HabitFlowColors.TextSecondary),
    HabitIconOption("🌙", Color(0xFF475569)),
    HabitIconOption("🌱", HabitFlowColors.Success)
)
