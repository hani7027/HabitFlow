package com.hk.habitflow.ui.navigation

/**
 * App-level routes. Login is the initial screen; after success navigate to Home.
 */
sealed class AppRoute {
    data object Login : AppRoute()
    data object Home : AppRoute()
}
