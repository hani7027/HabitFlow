package com.hk.habitflow.task.model

import androidx.compose.ui.graphics.Color

data class TaskUi(
    val id: String,
    val title: String,
    val description: String,
    val category: TaskCategory,
    val priority: TaskPriority,
    val time: String,
    val isCompleted: Boolean
) {
    val categoryColor: Color get() = category.color
    val priorityColor: Color get() = priority.color
}
