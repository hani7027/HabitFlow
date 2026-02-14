package com.hk.habitflow

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.hk.habitflow.database.HabitFlowDatabase
import com.hk.habitflow.database.initializeDatabase
import com.hk.habitflow.domain.model.User
import com.hk.habitflow.domain.repository.UserRepository
import com.hk.habitflow.di.habitFlowModule
import com.hk.habitflow.ui.navigation.AppNavGraph
import com.hk.habitflow.ui.navigation.AppRoute
import com.hk.habitflow.ui.navigation.MainTab
import com.hk.habitflow.ui.theme.HabitFlowTheme
import org.koin.compose.KoinApplication
import org.koin.compose.koinInject
import org.koin.dsl.koinConfiguration

@Composable
@Preview
fun App() {
    KoinApplication(
        configuration = koinConfiguration(declaration = { modules(habitFlowModule) }),
        content = {
            HabitFlowTheme {
                DatabaseInit()
                AppContent()
            }
        })
}

@Composable
private fun DatabaseInit() {
    val database: HabitFlowDatabase = koinInject()
    val userRepository: UserRepository = koinInject()
    LaunchedEffect(database) {
        initializeDatabase(database)
        if (userRepository.getByEmail("admin@habitflow.com") == null) {
            userRepository.insert(
                User(
                    id = "user_1",
                    name = "Admin",
                    email = "admin@habitflow.com",
                    passwordHash = "admin123",
                    createdAt = 0L
                )
            )
        }
    }
}

@Composable
fun AppContent() {
    var currentRoute by remember { mutableStateOf<AppRoute>(AppRoute.Login) }
    var mainTab by remember { mutableStateOf(MainTab.Home) }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .windowInsetsPadding(WindowInsets.statusBars)
    ) {
        AppNavGraph(
            currentRoute = currentRoute,
            mainTab = mainTab,
            onNavigate = { currentRoute = it },
            onMainTabSelected = { mainTab = it }
        )
    }
}
