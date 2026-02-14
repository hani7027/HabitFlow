package com.hk.habitflow.task

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hk.habitflow.domain.model.TaskWithDetails
import com.hk.habitflow.domain.repository.TaskRepository
import com.hk.habitflow.session.SessionHolder
import com.hk.habitflow.task.model.TaskCategory
import com.hk.habitflow.task.model.TaskPriority
import com.hk.habitflow.task.model.TaskUi
import com.hk.habitflow.task.util.PlatformClock
import com.hk.habitflow.task.util.startOfDayEpoch
import com.hk.habitflow.task.util.epochFromDayAndTime
import com.hk.habitflow.task.util.hourMinuteFromEpoch
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class TasksViewModel(
    private val taskRepository: TaskRepository
) : ViewModel() {

    private val _state = MutableStateFlow(TasksState(tasks = emptyList()))
    val state = _state.asStateFlow()

    private val _effect = Channel<TasksEffect>(Channel.BUFFERED)
    val effect = _effect.receiveAsFlow()

    init {
        viewModelScope.launch {
            val userId = SessionHolder.userId ?: return@launch
            taskRepository.observeTasksByUserId(userId).collect { list ->
                val uiList = list.map { it.toTaskUi() }
                _state.update { state ->
                    state.copy(
                        tasks = uiList,
                        totalCount = uiList.size,
                        completedCount = uiList.count { it.isCompleted }
                    )
                }
            }
        }
    }

    fun onEvent(event: TasksEvent) {
        when (event) {
            TasksEvent.SearchClick -> viewModelScope.launch { _effect.send(TasksEffect.OpenSearch) }
            is TasksEvent.SelectTab -> _state.update { it.copy(selectedTab = event.tab) }
            is TasksEvent.TaskChecked -> {
                val userId = SessionHolder.userId ?: return
                viewModelScope.launch {
                    val full = taskRepository.getTaskById(userId, event.taskId) ?: return@launch
                    taskRepository.updateTask(
                        full.copy(isCompleted = event.checked, completedAt = if (event.checked) PlatformClock.currentTimeMillis() else null)
                    )
                    val uiList = taskRepository.observeTasksByUserId(userId).first().map { it.toTaskUi() }
                    _state.update {
                        it.copy(
                            tasks = uiList,
                            totalCount = uiList.size,
                            completedCount = uiList.count { t -> t.isCompleted }
                        )
                    }
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
                    addTaskPriority = null,
                    addTaskDueDateTimeEpochMs = null,
                    addTaskReminderEnabled = false
                )
            }
            is TasksEvent.AddTaskTitleChange -> _state.update { it.copy(addTaskTitle = event.value) }
            is TasksEvent.AddTaskDescriptionChange -> _state.update { it.copy(addTaskDescription = event.value) }
            is TasksEvent.AddTaskCategorySelect -> _state.update { it.copy(addTaskCategory = event.category) }
            is TasksEvent.AddTaskPrioritySelect -> _state.update { it.copy(addTaskPriority = event.priority) }
            is TasksEvent.AddTaskDueDatePicked -> {
                val current = _state.value.addTaskDueDateTimeEpochMs
                val merged = if (current != null) epochFromDayAndTime(event.dayStartEpochMs, hourMinuteFromEpoch(current).first, hourMinuteFromEpoch(current).second)
                else event.dayStartEpochMs
                _state.update { it.copy(addTaskDueDateTimeEpochMs = merged) }
            }
            is TasksEvent.AddTaskDueTimePicked -> {
                val current = _state.value.addTaskDueDateTimeEpochMs
                val dayStart = if (current != null) startOfDayEpoch(current) else PlatformClock.currentTimeMillis().let { startOfDayEpoch(it) }
                val merged = epochFromDayAndTime(dayStart, event.hour, event.minute)
                _state.update { it.copy(addTaskDueDateTimeEpochMs = merged) }
            }
            is TasksEvent.AddTaskReminderChange -> _state.update { it.copy(addTaskReminderEnabled = event.enabled) }
            TasksEvent.SaveTask -> saveNewTask()
        }
    }

    private fun saveNewTask() {
        val state = _state.value
        val title = state.addTaskTitle.trim()
        if (title.isEmpty()) return
        val userId = SessionHolder.userId ?: return
        viewModelScope.launch {
            val categories = taskRepository.getTaskCategories().first()
            val priorities = taskRepository.getTaskPriorities().first()
            val category = state.addTaskCategory ?: TaskCategory.Other
            val priority = state.addTaskPriority ?: TaskPriority.Medium
            val categoryId = categories.find { it.name == category.label }?.id ?: categories.first().id
            val priorityId = priorities.find { it.name == priority.label }?.id ?: priorities.first().id
            val now = PlatformClock.currentTimeMillis()
            val taskId = "task_${now}_${kotlin.random.Random.nextLong().toString(36)}"
            val dueEpoch = state.addTaskDueDateTimeEpochMs
            val reminderEpoch = if (state.addTaskReminderEnabled && dueEpoch != null) dueEpoch - 15 * 60 * 1000 else null
            val newTask = TaskWithDetails(
                id = taskId,
                userId = userId,
                title = title,
                description = state.addTaskDescription.trim().takeIf { it.isNotEmpty() },
                categoryId = categoryId,
                categoryName = category.label,
                categoryIcon = null,
                priorityId = priorityId,
                priorityName = priority.label,
                priorityColor = null,
                dueDate = dueEpoch,
                reminderTime = reminderEpoch,
                isCompleted = false,
                createdAt = now,
                completedAt = null
            )
            taskRepository.insertTask(newTask)
            val uiList = taskRepository.observeTasksByUserId(userId).first().map { it.toTaskUi() }
            _state.update {
                it.copy(
                    tasks = uiList,
                    totalCount = uiList.size,
                    completedCount = uiList.count { t -> t.isCompleted },
                    showAddTaskSheet = false,
                    addTaskTitle = "",
                    addTaskDescription = "",
                    addTaskCategory = null,
                    addTaskPriority = null,
                    addTaskDueDateTimeEpochMs = null,
                    addTaskReminderEnabled = false
                )
            }
        }
    }
}
