package com.hk.habitflow.ui.theme

import androidx.compose.material3.ColorScheme
import androidx.compose.runtime.Composable

@Composable
actual fun rememberHabitFlowColorScheme(darkTheme: Boolean): ColorScheme =
    if (darkTheme) habitFlowDarkColorScheme() else habitFlowLightColorScheme()
