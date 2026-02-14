package com.hk.habitflow.task

import com.hk.habitflow.task.model.TaskCategory
import com.hk.habitflow.task.model.TaskPriority
import com.hk.habitflow.task.model.TaskUi

data class TasksState(
    val dateText: String = "Monday, January 29",
    val selectedTab: TasksTab = TasksTab.Today,
    val completedCount: Int = 0,
    val totalCount: Int = 0,
    val tasks: List<TaskUi> = emptyList(),
    val showAddTaskSheet: Boolean = false,
    val addTaskTitle: String = "",
    val addTaskDescription: String = "",
    val addTaskCategory: TaskCategory? = null,
    val addTaskPriority: TaskPriority? = null,
    val addTaskDueDateTimeEpochMs: Long? = null,
    val addTaskReminderEnabled: Boolean = false
)

enum class TasksTab { Today, Upcoming, Completed }

sealed class TasksEvent {
    object SearchClick : TasksEvent()
    data class SelectTab(val tab: TasksTab) : TasksEvent()
    data class TaskChecked(val taskId: String, val checked: Boolean) : TasksEvent()
    data class TaskOptions(val taskId: String) : TasksEvent()
    object AddTaskClick : TasksEvent()
    object DismissAddTaskSheet : TasksEvent()
    data class AddTaskTitleChange(val value: String) : TasksEvent()
    data class AddTaskDescriptionChange(val value: String) : TasksEvent()
    data class AddTaskCategorySelect(val category: TaskCategory) : TasksEvent()
    data class AddTaskPrioritySelect(val priority: TaskPriority) : TasksEvent()
    data class AddTaskDueDatePicked(val dayStartEpochMs: Long) : TasksEvent()
    data class AddTaskDueTimePicked(val hour: Int, val minute: Int) : TasksEvent()
    data class AddTaskReminderChange(val enabled: Boolean) : TasksEvent()
    object SaveTask : TasksEvent()
}

sealed class TasksEffect {
    object OpenSearch : TasksEffect()
}
