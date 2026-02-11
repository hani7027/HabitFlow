package com.hk.habitflow.task

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hk.habitflow.task.model.TaskCategory
import com.hk.habitflow.task.model.TaskPriority
import com.hk.habitflow.task.model.TaskUi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class TasksViewModel : ViewModel() {

    private val _state = MutableStateFlow(TasksState(tasks = sampleTasks()))
    val state = _state.asStateFlow()

    private val _effect = Channel<TasksEffect>(Channel.BUFFERED)
    val effect = _effect.receiveAsFlow()

    fun onEvent(event: TasksEvent) {
        when (event) {
            TasksEvent.SearchClick -> viewModelScope.launch { _effect.send(TasksEffect.OpenSearch) }
            is TasksEvent.SelectTab -> _state.update { it.copy(selectedTab = event.tab) }
            is TasksEvent.TaskChecked -> {
                _state.update { state ->
                    val list = state.tasks.map { t ->
                        if (t.id == event.taskId) t.copy(isCompleted = event.checked) else t
                    }
                    val completed = list.count { it.isCompleted }
                    state.copy(
                        tasks = list,
                        completedCount = completed
                    )
                }
            }
            is TasksEvent.TaskOptions -> { /* TODO: show options menu */ }
            TasksEvent.AddTaskClick -> _state.update { it.copy(showAddTaskSheet = true) }
            TasksEvent.DismissAddTaskSheet -> _state.update {
                it.copy(
                    showAddTaskSheet = false,
                    addTaskTitle = "",
                    addTaskDescription = "",
                    addTaskCategory = null,
                    addTaskPriority = null
                )
            }
            is TasksEvent.AddTaskTitleChange -> _state.update { it.copy(addTaskTitle = event.value) }
            is TasksEvent.AddTaskDescriptionChange -> _state.update { it.copy(addTaskDescription = event.value) }
            is TasksEvent.AddTaskCategorySelect -> _state.update { it.copy(addTaskCategory = event.category) }
            is TasksEvent.AddTaskPrioritySelect -> _state.update { it.copy(addTaskPriority = event.priority) }
            is TasksEvent.AddTaskDueDateChange -> _state.update { it.copy(addTaskDueDate = event.value) }
            is TasksEvent.AddTaskDueTimeChange -> _state.update { it.copy(addTaskDueTime = event.value) }
            is TasksEvent.AddTaskReminderChange -> _state.update { it.copy(addTaskReminderEnabled = event.enabled) }
            TasksEvent.SaveTask -> saveNewTask()
        }
    }

    private fun saveNewTask() {
        val state = _state.value
        val title = state.addTaskTitle.trim()
        if (title.isEmpty()) return
        val category = state.addTaskCategory ?: TaskCategory.Other
        val priority = state.addTaskPriority ?: TaskPriority.Medium
        val newTask = TaskUi(
            id = "task-${state.tasks.size}-${kotlin.random.Random.nextLong().toString(36)}",
            title = title,
            description = state.addTaskDescription.trim(),
            category = category,
            priority = priority,
            time = state.addTaskDueTime.ifEmpty { "--:--" },
            isCompleted = false
        )
        _state.update {
            it.copy(
                tasks = it.tasks + newTask,
                totalCount = it.totalCount + 1,
                showAddTaskSheet = false,
                addTaskTitle = "",
                addTaskDescription = "",
                addTaskCategory = null,
                addTaskPriority = null,
                addTaskDueDate = "",
                addTaskDueTime = "",
                addTaskReminderEnabled = false
            )
        }
    }

    private fun sampleTasks(): List<TaskUi> = listOf(
        TaskUi(
            id = "1",
            title = "Review quarterly budget report",
            description = "Analyze Q4 expenses and prepare presentation",
            category = TaskCategory.Work,
            priority = TaskPriority.High,
            time = "10:30 AM",
            isCompleted = false
        ),
        TaskUi(
            id = "2",
            title = "Call dentist for appointment",
            description = "Schedule routine cleaning appointment",
            category = TaskCategory.Personal,
            priority = TaskPriority.Medium,
            time = "2:00 PM",
            isCompleted = false
        ),
        TaskUi(
            id = "3",
            title = "Grocery shopping",
            description = "Buy ingredients for dinner and weekly essentials",
            category = TaskCategory.Personal,
            priority = TaskPriority.Low,
            time = "6:00 PM",
            isCompleted = false
        )
    )
}
