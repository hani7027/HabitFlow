package com.hk.habitflow.habit

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.hk.habitflow.habit.create.CreateHabitViewModel
import com.hk.habitflow.habit.ui.CreateHabitScreen
import com.hk.habitflow.habit.ui.HabitsContent

@Composable
fun HabitsScreen(
    habitsViewModel: HabitsViewModel,
    createHabitViewModel: CreateHabitViewModel
) {
    val state by habitsViewModel.state.collectAsStateWithLifecycle()
    var showCreateHabit by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        habitsViewModel.effect.collect { _ -> }
    }

    if (showCreateHabit) {
        CreateHabitScreen(
            viewModel = createHabitViewModel,
            onBack = {
                showCreateHabit = false
                habitsViewModel.onEvent(HabitsEvent.Refresh)
            },
            onHabitCreated = { }
        )
    } else {
        HabitsContent(
            state = state,
            onEvent = habitsViewModel::onEvent,
            onAddClick = { showCreateHabit = true }
        )
    }
}
