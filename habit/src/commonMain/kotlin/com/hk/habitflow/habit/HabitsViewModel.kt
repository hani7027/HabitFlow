package com.hk.habitflow.habit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hk.habitflow.habit.model.HabitUi
import com.hk.habitflow.ui.theme.HabitFlowColors
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HabitsViewModel : ViewModel() {

    private val _state = MutableStateFlow(HabitsState(
        habits = sampleHabits(),
        weekDayStatuses = sampleWeekDayStatuses()
    ))
    val state = _state.asStateFlow()

    private val _effect = Channel<HabitsEffect>(Channel.BUFFERED)
    val effect = _effect.receiveAsFlow()

    init {
        syncCompletedRemaining()
    }

    fun onEvent(event: HabitsEvent) {
        when (event) {
            is HabitsEvent.HabitClicked -> { }
            is HabitsEvent.HabitCompleteToggled -> toggleComplete(event.habitId)
            is HabitsEvent.HabitProgressIncrement -> incrementProgress(event.habitId)
            HabitsEvent.CalendarClick -> viewModelScope.launch { _effect.send(HabitsEffect.OpenCalendar) }
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

    private fun syncCompletedRemaining() {
        val state = _state.value
        val completed = state.habits.count { it.isCompletedToday }
        _state.update { it.copy(completedCount = completed, remainingCount = (it.habits.size - completed).coerceAtLeast(0)) }
    }

    private fun sampleHabits(): List<HabitUi> = listOf(
        HabitUi(
            id = "1",
            name = "Morning Workout",
            description = "30 minutes daily",
            iconEmoji = "💪",
            iconColor = HabitFlowColors.Info,
            streakDays = 12,
            isCompletedToday = true
        ),
        HabitUi(
            id = "2",
            name = "Drink Water",
            description = "8 glasses daily",
            iconEmoji = "💧",
            iconColor = HabitFlowColors.Success,
            streakDays = 8,
            isCompletedToday = false,
            progressCurrent = 6,
            progressTarget = 8
        ),
        HabitUi(
            id = "3",
            name = "Read Books",
            description = "30 minutes daily",
            iconEmoji = "📖",
            iconColor = HabitFlowColors.Primary,
            streakDays = 25,
            isCompletedToday = true
        ),
        HabitUi(
            id = "4",
            name = "Sleep Early",
            description = "Before 10:30 PM",
            iconEmoji = "🛏",
            iconColor = HabitFlowColors.Focus,
            streakDays = 5,
            isCompletedToday = false
        ),
        HabitUi(
            id = "5",
            name = "Meditation",
            description = "15 minutes daily",
            iconEmoji = "🧘",
            iconColor = HabitFlowColors.CategoryOther,
            streakDays = 3,
            isCompletedToday = true
        )
    )

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
