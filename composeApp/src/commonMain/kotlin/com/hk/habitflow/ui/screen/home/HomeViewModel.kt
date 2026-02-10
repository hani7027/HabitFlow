package com.hk.habitflow.ui.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hk.habitflow.ui.component.HabitCardUi
import com.hk.habitflow.ui.component.TaskItemUi
import com.hk.habitflow.ui.theme.HabitFlowColors
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {

    private val _state = MutableStateFlow(HomeState(
        tasks = sampleTasks(),
        habits = sampleHabits()
    ))
    val state = _state.asStateFlow()

    private val _effect = Channel<HomeEffect>(Channel.BUFFERED)
    val effect = _effect.receiveAsFlow()

    fun onEvent(event: HomeEvent) {
        when (event) {
            is HomeEvent.TaskChecked -> {
                _state.update { state ->
                    val task = state.tasks.find { it.id == event.taskId } ?: return@update state
                    if (task.isCompleted == event.checked) return@update state
                    val delta = if (event.checked) 1 else -1
                    state.copy(
                        tasks = state.tasks.map { t ->
                            if (t.id == event.taskId) t.copy(isCompleted = event.checked) else t
                        },
                        completedCount = (state.completedCount + delta).coerceIn(0, state.totalCount)
                    )
                }
            }
            is HomeEvent.HabitClicked -> {
                // TODO: record habit completion when repo exists
            }
            HomeEvent.ViewAllTasks -> viewModelScope.launch {
                _effect.send(HomeEffect.NavigateToTasks)
            }
            HomeEvent.ViewAllHabits -> viewModelScope.launch {
                _effect.send(HomeEffect.NavigateToHabits)
            }
            HomeEvent.StartFocus -> viewModelScope.launch {
                _effect.send(HomeEffect.NavigateToFocus)
            }
        }
    }

    private fun sampleTasks(): List<TaskItemUi> = listOf(
        TaskItemUi(
            id = "1",
            title = "Review project proposal",
            categoryLabel = "Work",
            categoryColor = HabitFlowColors.CategoryWork,
            time = "10:00 AM",
            priorityColor = HabitFlowColors.PriorityHigh,
            isCompleted = false
        ),
        TaskItemUi(
            id = "2",
            title = "Team standup meeting",
            categoryLabel = "Work",
            categoryColor = HabitFlowColors.Info,
            time = "9:00 AM",
            priorityColor = HabitFlowColors.PriorityMedium,
            isCompleted = true
        ),
        TaskItemUi(
            id = "3",
            title = "Buy groceries",
            categoryLabel = "Personal",
            categoryColor = HabitFlowColors.CategoryPersonal,
            time = "6:00 PM",
            priorityColor = HabitFlowColors.PriorityLow,
            isCompleted = false
        )
    )

    private fun sampleHabits(): List<HabitCardUi> = listOf(
        HabitCardUi(
            id = "1",
            name = "Drink Water",
            progressText = "6/8 cups",
            streakDays = 12,
            cardColor = HabitFlowColors.Info
        ),
        HabitCardUi(
            id = "2",
            name = "Exercise",
            progressText = "30 min",
            streakDays = 8,
            cardColor = HabitFlowColors.Success
        ),
        HabitCardUi(
            id = "3",
            name = "Read",
            progressText = "20 pages",
            streakDays = 5,
            cardColor = HabitFlowColors.Primary
        )
    )
}
