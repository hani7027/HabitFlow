package com.hk.habitflow.ui.screen.home

import com.hk.habitflow.domain.model.HabitWithDetails
import com.hk.habitflow.domain.model.TaskWithDetails
import com.hk.habitflow.task.toTaskUi
import com.hk.habitflow.ui.component.HabitCardUi
import com.hk.habitflow.ui.component.TaskItemUi
import com.hk.habitflow.ui.theme.HabitFlowColors

private val habitCardColors = listOf(
    HabitFlowColors.Info,
    HabitFlowColors.Success,
    HabitFlowColors.Primary,
    HabitFlowColors.Focus,
    HabitFlowColors.CategoryOther
)

fun TaskWithDetails.toTaskItemUi(): TaskItemUi {
    val t = toTaskUi()
    return TaskItemUi(
        id = t.id,
        title = t.title,
        categoryLabel = t.category.label,
        categoryColor = t.categoryColor,
        time = t.time,
        priorityColor = t.priorityColor,
        isCompleted = t.isCompleted
    )
}

fun HabitWithDetails.toHabitCardUi(): HabitCardUi {
    val colorIndex = id.hashCode().and(Int.MAX_VALUE) % habitCardColors.size
    val progressText = frequencyTypeName.ifEmpty { "Target: $targetValue" }
    return HabitCardUi(
        id = id,
        name = name,
        progressText = progressText,
        streakDays = 0,
        cardColor = habitCardColors[colorIndex]
    )
}
