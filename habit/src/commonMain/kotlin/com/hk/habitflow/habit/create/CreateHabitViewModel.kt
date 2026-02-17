package com.hk.habitflow.habit.create

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hk.habitflow.domain.model.HabitWithDetails
import com.hk.habitflow.domain.repository.HabitRepository
import com.hk.habitflow.session.SessionHolder
import com.hk.habitflow.habit.util.PlatformClock
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

sealed class CreateHabitEffect {
    object Dismiss : CreateHabitEffect()
}

class CreateHabitViewModel(
    private val habitRepository: HabitRepository
) : ViewModel() {

    private val _state = MutableStateFlow(CreateHabitState())
    val state = _state.asStateFlow()

    private val _effect = Channel<CreateHabitEffect>(Channel.BUFFERED)
    val effect = _effect.receiveAsFlow()

    fun onEvent(event: CreateHabitEvent) {
        when (event) {
            is CreateHabitEvent.NameChange -> _state.update { it.copy(habitName = event.value) }
            is CreateHabitEvent.IconSelect -> _state.update { it.copy(selectedIconIndex = event.index) }
            is CreateHabitEvent.FrequencySelect -> _state.update { it.copy(frequency = event.frequency) }
            is CreateHabitEvent.TargetTypeSelect -> _state.update { it.copy(targetType = event.type) }
            is CreateHabitEvent.TargetValueChange -> _state.update { it.copy(targetValue = event.value) }
            is CreateHabitEvent.TargetUnitChange -> _state.update { it.copy(targetUnit = event.value) }
            is CreateHabitEvent.ReminderChange -> _state.update { it.copy(reminderEnabled = event.enabled) }
            CreateHabitEvent.Back -> viewModelScope.launch { _effect.send(CreateHabitEffect.Dismiss) }
            CreateHabitEvent.Create -> createHabit()
        }
    }

    private fun createHabit() = viewModelScope.launch {
        val state = _state.value
        val name = state.habitName.trim()
        if (name.isBlank()) return@launch
        val userId = SessionHolder.userId ?: return@launch
        val icons = habitRepository.getHabitIcons().first()
        val frequencyTypes = habitRepository.getHabitFrequencyTypes().first()
        val iconId = icons.getOrNull(state.selectedIconIndex)?.id ?: icons.first().id
        val frequencyTypeName = when (state.frequency) {
            HabitFrequency.Daily -> "Daily"
            HabitFrequency.CustomDays -> "Custom"
        }
        val frequencyType = frequencyTypes.find { it.name == frequencyTypeName } ?: frequencyTypes.first()
        val targetValue = (state.targetValue.toLongOrNull() ?: 0L).coerceAtLeast(0L)
        val now = PlatformClock.currentTimeMillis()
        val habitId = "habit_${now}_${kotlin.random.Random.nextLong().toString(36)}"
        val habit = HabitWithDetails(
            id = habitId,
            userId = userId,
            name = name,
            iconId = iconId,
            iconName = icons.find { it.id == iconId }?.name ?: "",
            frequencyTypeId = frequencyType.id,
            frequencyTypeName = frequencyType.name,
            frequencyTypeDescription = frequencyType.description,
            targetValue = targetValue,
            reminderTime = null,
            createdAt = now,
            isArchived = false
        )
        habitRepository.insertHabit(habit)
        _effect.send(CreateHabitEffect.Dismiss)
    }
}
