package com.hk.habitflow.habit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hk.habitflow.domain.repository.HabitRepository
import com.hk.habitflow.habit.model.HabitUi
import com.hk.habitflow.session.SessionHolder
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HabitsViewModel(
    private val habitRepository: HabitRepository
) : ViewModel() {

    private val _state = MutableStateFlow(HabitsState(
        habits = emptyList(),
        weekDayStatuses = sampleWeekDayStatuses()
    ))
    val state = _state.asStateFlow()

    private val _effect = Channel<HabitsEffect>(Channel.BUFFERED)
    val effect = _effect.receiveAsFlow()

    init {
        viewModelScope.launch {
            val userId = SessionHolder.userId ?: return@launch
            habitRepository.observeHabitsByUserId(userId).collect { list ->
                val uiList = list.map { it.toHabitUi() }
                val completed = uiList.count { it.isCompletedToday }
                _state.update {
                    it.copy(
                        habits = uiList,
                        completedCount = completed,
                        remainingCount = (uiList.size - completed).coerceAtLeast(0)
                    )
                }
            }
        }
    }

    fun onEvent(event: HabitsEvent) {
        when (event) {
            is HabitsEvent.HabitClicked -> { }
            is HabitsEvent.HabitCompleteToggled -> toggleComplete(event.habitId)
            is HabitsEvent.HabitProgressIncrement -> incrementProgress(event.habitId)
            is HabitsEvent.AddHabit -> { }
            HabitsEvent.CalendarClick -> viewModelScope.launch { _effect.send(HabitsEffect.OpenCalendar) }
            HabitsEvent.Refresh -> refresh()
        }
    }

    fun refresh() {
        viewModelScope.launch {
            val userId = SessionHolder.userId ?: return@launch
            val list = habitRepository.observeHabitsByUserId(userId).first().map { it.toHabitUi() }
            val completed = list.count { it.isCompletedToday }
            _state.update {
                it.copy(
                    habits = list,
                    completedCount = completed,
                    remainingCount = (list.size - completed).coerceAtLeast(0)
                )
            }
        }
    }

    private fun toggleComplete(habitId: String) {
        _state.update { state ->
            val list = state.habits.map { h ->
                if (h.id == habitId) {
                    when {
                        h.isCountable -> h.copy(
                            progressCurrent = h.progressTarget!!,
                            isCompletedToday = true
                        )
                        else -> h.copy(isCompletedToday = !h.isCompletedToday)
                    }
                } else h
            }
            val completed = list.count { it.isCompletedToday }
            state.copy(habits = list, completedCount = completed, remainingCount = (list.size - completed).coerceAtLeast(0))
        }
    }

    private fun incrementProgress(habitId: String) {
        _state.update { state ->
            val list = state.habits.map { h ->
                if (h.id == habitId && h.isCountable) {
                    val target = h.progressTarget!!
                    val next = (h.progressCurrent + 1).coerceAtMost(target)
                    h.copy(
                        progressCurrent = next,
                        isCompletedToday = next >= target
                    )
                } else h
            }
            val completed = list.count { it.isCompletedToday }
            state.copy(habits = list, completedCount = completed, remainingCount = (list.size - completed).coerceAtLeast(0))
        }
    }

    private fun sampleWeekDayStatuses(): List<WeekDayStatus> = listOf(
        WeekDayStatus.Completed,
        WeekDayStatus.Completed,
        WeekDayStatus.Completed,
        WeekDayStatus.Completed,
        WeekDayStatus.Completed,
        WeekDayStatus.Partial,
        WeekDayStatus.Incomplete
    )
}
