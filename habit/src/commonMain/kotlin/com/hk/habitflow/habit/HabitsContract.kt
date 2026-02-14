package com.hk.habitflow.habit

import com.hk.habitflow.habit.model.HabitUi

data class HabitsState(
    val completedCount: Int = 4,
    val remainingCount: Int = 1,
    val habits: List<HabitUi> = emptyList(),
    val weekDayStatuses: List<WeekDayStatus> = emptyList()
)

enum class WeekDayStatus { Completed, Partial, Incomplete }

sealed class HabitsEvent {
    data class HabitClicked(val habitId: String) : HabitsEvent()
    data class HabitCompleteToggled(val habitId: String) : HabitsEvent()
    data class HabitProgressIncrement(val habitId: String) : HabitsEvent()
    data class AddHabit(val habit: HabitUi) : HabitsEvent()
    object CalendarClick : HabitsEvent()
    object Refresh : HabitsEvent()
}

sealed class HabitsEffect {
    object OpenCalendar : HabitsEffect()
}
