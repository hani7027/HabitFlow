package com.hk.habitflow.ui.screen.main

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.hk.habitflow.habit.HabitsScreen
import com.hk.habitflow.habit.HabitsViewModel
import com.hk.habitflow.habit.create.CreateHabitViewModel
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
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = MaterialTheme.colorScheme.background,
        contentColor = MaterialTheme.colorScheme.onBackground,
        bottomBar = {
            MainBottomNav(
                currentTab = selectedTab,
                onTabSelected = onTabSelected,
                modifier = Modifier.windowInsetsPadding(WindowInsets.navigationBars)
            )
        }
    ) { paddingValues ->
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            color = MaterialTheme.colorScheme.background
        ) {
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
                    val habitsViewModel: HabitsViewModel = koinViewModel()
                    val createHabitViewModel: CreateHabitViewModel = koinViewModel()
                    HabitsScreen(
                        habitsViewModel = habitsViewModel,
                        createHabitViewModel = createHabitViewModel
                    )
                }
            }
        }
    }
}
