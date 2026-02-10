package com.hk.habitflow.ui.screen.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun HomeScreen(
    viewModel: HomeViewModel
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    HomeContent(
        state = state,
        onEvent = viewModel::onEvent
    )

    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is HomeEffect.NavigateToTasks -> { /* TODO: NavController.navigate(Tasks) */ }
                is HomeEffect.NavigateToHabits -> { /* TODO: NavController.navigate(Habits) */ }
                is HomeEffect.NavigateToFocus -> { /* TODO: NavController.navigate(Focus) */ }
            }
        }
    }
}
