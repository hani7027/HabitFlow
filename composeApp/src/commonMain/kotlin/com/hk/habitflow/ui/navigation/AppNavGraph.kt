package com.hk.habitflow.ui.navigation

import androidx.compose.runtime.Composable
import com.hk.habitflow.ui.screen.home.HomeScreen
import com.hk.habitflow.ui.screen.home.HomeViewModel
import com.hk.habitflow.ui.screen.login.LoginScreen
import com.hk.habitflow.ui.screen.login.LoginViewModel
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun AppNavGraph(
    currentRoute: AppRoute,
    onNavigate: (AppRoute) -> Unit
) {
    when (currentRoute) {
        is AppRoute.Login -> {
            val viewModel: LoginViewModel = koinViewModel()
            LoginScreen(
                viewModel = viewModel,
                onNavigateToHome = { onNavigate(AppRoute.Home) }
            )
        }
        is AppRoute.Home -> {
            val viewModel: HomeViewModel = koinViewModel()
            HomeScreen(viewModel = viewModel)
        }
    }
}
