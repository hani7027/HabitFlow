package com.hk.habitflow.ui.navigation

import androidx.compose.runtime.Composable
import com.hk.habitflow.ui.screen.login.LoginScreen
import com.hk.habitflow.ui.screen.login.LoginViewModel
import com.hk.habitflow.ui.screen.main.MainScreen
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun AppNavGraph(
    currentRoute: AppRoute,
    mainTab: MainTab,
    onNavigate: (AppRoute) -> Unit,
    onMainTabSelected: (MainTab) -> Unit
) {
    when (currentRoute) {
        is AppRoute.Login -> {
            val viewModel: LoginViewModel = koinViewModel()
            LoginScreen(
                viewModel = viewModel,
                onNavigateToHome = { onNavigate(AppRoute.Main) }
            )
        }
        is AppRoute.Main -> {
            MainScreen(
                selectedTab = mainTab,
                onTabSelected = onMainTabSelected
            )
        }
    }
}
