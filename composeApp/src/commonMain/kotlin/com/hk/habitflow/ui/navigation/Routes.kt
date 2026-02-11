package com.hk.habitflow.ui.navigation

/**
 * App-level routes. Login is the initial screen; after success navigate to Main (with bottom nav).
 */
sealed class AppRoute {
    data object Login : AppRoute()
    data object Main : AppRoute()
}

/**
 * Tabs in the main bottom navigation.
 */
enum class MainTab {
    Home,
    Tasks,
    Habits
}
