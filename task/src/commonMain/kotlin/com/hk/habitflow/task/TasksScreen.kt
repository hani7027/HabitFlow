package com.hk.habitflow.task

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.hk.habitflow.task.ui.AddTaskSheet
import com.hk.habitflow.task.ui.TasksContent

@Composable
fun TasksScreen(
    viewModel: TasksViewModel
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    Box(modifier = Modifier.fillMaxSize()) {
        TasksContent(
            state = state,
            onEvent = viewModel::onEvent
        )
        if (state.showAddTaskSheet) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .align(Alignment.BottomCenter)
            ) {
                androidx.compose.foundation.Canvas(
                    modifier = Modifier
                        .fillMaxSize()
                        .align(Alignment.TopCenter)
                ) {
                    drawRect(color = androidx.compose.ui.graphics.Color.Black.copy(alpha = 0.4f))
                }
                AddTaskSheet(
                    modifier = Modifier.align(Alignment.BottomCenter),
                    title = state.addTaskTitle,
                    description = state.addTaskDescription,
                    selectedCategory = state.addTaskCategory,
                    selectedPriority = state.addTaskPriority,
                    dueDate = state.addTaskDueDate,
                    dueTime = state.addTaskDueTime,
                    reminderEnabled = state.addTaskReminderEnabled,
                    onTitleChange = { viewModel.onEvent(TasksEvent.AddTaskTitleChange(it)) },
                    onDescriptionChange = { viewModel.onEvent(TasksEvent.AddTaskDescriptionChange(it)) },
                    onCategorySelect = { viewModel.onEvent(TasksEvent.AddTaskCategorySelect(it)) },
                    onPrioritySelect = { viewModel.onEvent(TasksEvent.AddTaskPrioritySelect(it)) },
                    onDueDateChange = { viewModel.onEvent(TasksEvent.AddTaskDueDateChange(it)) },
                    onDueTimeChange = { viewModel.onEvent(TasksEvent.AddTaskDueTimeChange(it)) },
                    onReminderChange = { viewModel.onEvent(TasksEvent.AddTaskReminderChange(it)) },
                    onDismiss = { viewModel.onEvent(TasksEvent.DismissAddTaskSheet) },
                    onSave = { viewModel.onEvent(TasksEvent.SaveTask) }
                )
            }
        }
    }

    LaunchedEffect(Unit) {
        viewModel.effect.collect { _ -> }
    }
}
