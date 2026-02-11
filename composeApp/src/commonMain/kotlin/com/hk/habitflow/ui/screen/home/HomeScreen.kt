package com.hk.habitflow.ui.screen.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
    onNavigateToTasks: () -> Unit = {},
    onNavigateToHabits: () -> Unit = {},
    onNavigateToFocus: () -> Unit = {}
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    HomeContent(
        state = state,
        onEvent = viewModel::onEvent
    )

    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is HomeEffect.NavigateToTasks -> onNavigateToTasks()
                is HomeEffect.NavigateToHabits -> onNavigateToHabits()
                is HomeEffect.NavigateToFocus -> onNavigateToFocus()
            }
        }
    }
}
