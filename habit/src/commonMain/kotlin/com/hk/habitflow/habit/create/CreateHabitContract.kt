package com.hk.habitflow.habit.create

data class CreateHabitState(
    val habitName: String = "",
    val selectedIconIndex: Int = 0,
    val frequency: HabitFrequency = HabitFrequency.Daily,
    val targetType: DailyTargetType = DailyTargetType.Time,
    val targetValue: String = "30",
    val targetUnit: String = "minutes",
    val reminderEnabled: Boolean = false
)

enum class HabitFrequency { Daily, CustomDays }
enum class DailyTargetType { Time, Count }

sealed class CreateHabitEvent {
    data class NameChange(val value: String) : CreateHabitEvent()
    data class IconSelect(val index: Int) : CreateHabitEvent()
    data class FrequencySelect(val frequency: HabitFrequency) : CreateHabitEvent()
    data class TargetTypeSelect(val type: DailyTargetType) : CreateHabitEvent()
    data class TargetValueChange(val value: String) : CreateHabitEvent()
    data class TargetUnitChange(val value: String) : CreateHabitEvent()
    data class ReminderChange(val enabled: Boolean) : CreateHabitEvent()
    object Back : CreateHabitEvent()
    object Create : CreateHabitEvent()
}
