package com.hk.habitflow.ui.navigation

import androidx.compose.runtime.Composable
import com.hk.habitflow.ui.screen.main.MainScreen

@Composable
fun AppNavGraph(
    mainTab: MainTab,
    onMainTabSelected: (MainTab) -> Unit
) {
    MainScreen(
        selectedTab = mainTab,
        onTabSelected = onMainTabSelected
    )
}
