package com.hk.habitflow

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview
import com.hk.habitflow.di.habitFlowModule
import com.hk.habitflow.ui.navigation.AppNavGraph
import com.hk.habitflow.ui.navigation.AppRoute
import com.hk.habitflow.ui.theme.HabitFlowTheme
import org.koin.compose.KoinApplication
import org.koin.dsl.koinConfiguration

@Composable
@Preview
fun App() {
    KoinApplication(
        configuration = koinConfiguration(declaration = { modules(habitFlowModule) }),
        content = {
            HabitFlowTheme {
                AppContent()
            }
        })
}

@Composable
fun AppContent() {
    var currentRoute by remember { mutableStateOf<AppRoute>(AppRoute.Login) }
    AppNavGraph(
        currentRoute = currentRoute,
        onNavigate = { currentRoute = it }
    )
}
