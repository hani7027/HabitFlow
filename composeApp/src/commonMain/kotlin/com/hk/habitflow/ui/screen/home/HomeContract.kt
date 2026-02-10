package com.hk.habitflow.ui.screen.home

import com.hk.habitflow.ui.component.HabitCardUi
import com.hk.habitflow.ui.component.TaskItemUi

data class HomeState(
    val greeting: String = "Good Morning",
    val dateText: String = "Monday, January 29",
    val completedCount: Int = 15,
    val totalCount: Int = 22,
    val taskCount: Int = 8,
    val habitCount: Int = 7,
    val tasks: List<TaskItemUi> = emptyList(),
    val habits: List<HabitCardUi> = emptyList(),
    val focusMinutes: Int = 25
)

sealed class HomeEvent {
    data class TaskChecked(val taskId: String, val checked: Boolean) : HomeEvent()
    data class HabitClicked(val habitId: String) : HomeEvent()
    object ViewAllTasks : HomeEvent()
    object ViewAllHabits : HomeEvent()
    object StartFocus : HomeEvent()
}

sealed class HomeEffect {
    object NavigateToTasks : HomeEffect()
    object NavigateToHabits : HomeEffect()
    object NavigateToFocus : HomeEffect()
}
