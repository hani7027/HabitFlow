package com.hk.habitflow.task

import com.hk.habitflow.domain.model.TaskWithDetails
import com.hk.habitflow.task.model.TaskCategory
import com.hk.habitflow.task.model.TaskPriority
import com.hk.habitflow.task.model.TaskUi
import com.hk.habitflow.task.util.TimeFormatter

fun TaskWithDetails.toTaskUi(): TaskUi = TaskUi(
    id = id,
    title = title,
    description = description.orEmpty(),
    category = TaskCategory.entries.find { it.label == categoryName } ?: TaskCategory.Other,
    priority = TaskPriority.entries.find { it.label == priorityName } ?: TaskPriority.Medium,
    time = TimeFormatter.formatTime(reminderTime ?: dueDate),
    isCompleted = isCompleted
)
