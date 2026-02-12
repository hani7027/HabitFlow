package com.hk.habitflow.ui.screen.main

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.hk.habitflow.habit.HabitsScreen
import com.hk.habitflow.habit.HabitsViewModel
import com.hk.habitflow.task.TasksScreen
import com.hk.habitflow.task.TasksViewModel
import com.hk.habitflow.ui.component.MainBottomNav
import com.hk.habitflow.ui.navigation.MainTab
import com.hk.habitflow.ui.screen.home.HomeScreen
import com.hk.habitflow.ui.screen.home.HomeViewModel
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun MainScreen(
    selectedTab: MainTab,
    onTabSelected: (MainTab) -> Unit
) {
    Box(modifier = Modifier.fillMaxSize()) {
        when (selectedTab) {
                MainTab.Home -> {
                    val viewModel: HomeViewModel = koinViewModel()
                    HomeScreen(
                        viewModel = viewModel,
                        onNavigateToTasks = { onTabSelected(MainTab.Tasks) },
                        onNavigateToHabits = { onTabSelected(MainTab.Habits) }
                    )
                }
                MainTab.Tasks -> {
                    val viewModel: TasksViewModel = koinViewModel()
                    TasksScreen(viewModel = viewModel)
                }
            MainTab.Habits -> {
                val viewModel: HabitsViewModel = koinViewModel()
                HabitsScreen(viewModel = viewModel)
            }
        }
        MainBottomNav(
            currentTab = selectedTab,
            onTabSelected = onTabSelected,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .windowInsetsPadding(WindowInsets.navigationBars)
        )
    }
}
