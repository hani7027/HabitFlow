package com.hk.habitflow.habit

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.hk.habitflow.habit.ui.HabitsContent

@Composable
fun HabitsScreen(
    viewModel: HabitsViewModel
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    HabitsContent(
        state = state,
        onEvent = viewModel::onEvent
    )

    LaunchedEffect(Unit) {
        viewModel.effect.collect { _ -> }
    }
}
