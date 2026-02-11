package com.hk.habitflow.task.model

import androidx.compose.ui.graphics.Color
import com.hk.habitflow.ui.theme.HabitFlowColors

enum class TaskPriority(val label: String, val color: Color) {
    Low("Low", HabitFlowColors.PriorityLow),
    Medium("Medium", HabitFlowColors.PriorityMedium),
    High("High", HabitFlowColors.PriorityHigh)
}
