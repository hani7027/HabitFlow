package com.hk.habitflow.ui.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hk.habitflow.domain.repository.HabitRepository
import com.hk.habitflow.domain.repository.TaskRepository
import com.hk.habitflow.session.SessionHolder
import com.hk.habitflow.task.util.PlatformClock
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel(
    private val taskRepository: TaskRepository,
    private val habitRepository: HabitRepository
) : ViewModel() {

    private val _state = MutableStateFlow(HomeState())
    val state = _state.asStateFlow()

    private val _effect = Channel<HomeEffect>(Channel.BUFFERED)
    val effect = _effect.receiveAsFlow()

    init {
        val userId = SessionHolder.userId
        if (userId != null) {
            viewModelScope.launch {
                taskRepository.observeTasksByUserId(userId).collect { list ->
                    _state.update { s ->
                        s.copy(
                            tasks = list.take(5).map { it.toTaskItemUi() },
                            taskCount = list.size,
                            completedCount = list.count { it.isCompleted },
                            totalCount = list.size + s.habitCount
                        )
                    }
                }
            }
            viewModelScope.launch {
                habitRepository.observeHabitsByUserId(userId).collect { list ->
                    _state.update { s ->
                        s.copy(
                            habits = list.take(5).map { it.toHabitCardUi() },
                            habitCount = list.size,
                            totalCount = s.taskCount + list.size
                        )
                    }
                }
            }
        }
    }

    fun onEvent(event: HomeEvent) {
        when (event) {
            is HomeEvent.TaskChecked -> {
                val userId = SessionHolder.userId ?: return
                viewModelScope.launch {
                    val task = taskRepository.getTaskById(userId, event.taskId) ?: return@launch
                    taskRepository.updateTask(
                        task.copy(
                            isCompleted = event.checked,
                            completedAt = if (event.checked) PlatformClock.currentTimeMillis() else null
                        )
                    )
                    val list = taskRepository.observeTasksByUserId(userId).first()
                    _state.update { s ->
                        s.copy(
                            tasks = list.take(5).map { it.toTaskItemUi() },
                            taskCount = list.size,
                            completedCount = list.count { it.isCompleted },
                            totalCount = list.size + s.habitCount
                        )
                    }
                }
            }
            is HomeEvent.HabitClicked -> {
                // TODO: navigate to habit detail or record completion when API exists
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
}
