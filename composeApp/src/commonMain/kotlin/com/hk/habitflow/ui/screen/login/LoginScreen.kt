package com.hk.habitflow.ui.screen.login

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun LoginScreen(
    viewModel: LoginViewModel,
    onNavigateToHome: () -> Unit = {}
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LoginContent(
        state = state,
        onEvent = viewModel::onEvent
    )

    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is LoginContract.Effect.NavigateToHome -> onNavigateToHome()
                is LoginContract.Effect.ShowToast -> {
                    // TODO: Show toast (platform-specific or Snackbar)
                }
            }
        }
    }
}
