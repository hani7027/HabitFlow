package com.hk.habitflow.habit.create

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class CreateHabitViewModel : ViewModel() {

    private val _state = MutableStateFlow(CreateHabitState())
    val state = _state.asStateFlow()

    fun onEvent(event: CreateHabitEvent) {
        when (event) {
            is CreateHabitEvent.NameChange -> _state.update { it.copy(habitName = event.value) }
            is CreateHabitEvent.IconSelect -> _state.update { it.copy(selectedIconIndex = event.index) }
            is CreateHabitEvent.FrequencySelect -> _state.update { it.copy(frequency = event.frequency) }
            is CreateHabitEvent.TargetTypeSelect -> _state.update { it.copy(targetType = event.type) }
            is CreateHabitEvent.TargetValueChange -> _state.update { it.copy(targetValue = event.value) }
            is CreateHabitEvent.TargetUnitChange -> _state.update { it.copy(targetUnit = event.value) }
            is CreateHabitEvent.ReminderChange -> _state.update { it.copy(reminderEnabled = event.enabled) }
            CreateHabitEvent.Back -> { }
            CreateHabitEvent.Create -> { }
        }
    }
}
