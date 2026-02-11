package com.hk.habitflow.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf

@Composable
fun HabitFlowTheme(
    darkTheme: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) habitFlowDarkColorScheme() else habitFlowLightColorScheme()
    CompositionLocalProvider(
        LocalHabitFlowSpacing provides DefaultHabitFlowSpacing,
        LocalHabitFlowComponents provides DefaultHabitFlowComponents
    ) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = HabitFlowTypography,
            shapes = HabitFlowShapes,
            content = content
        )
    }
}

val LocalHabitFlowSpacing = staticCompositionLocalOf { DefaultHabitFlowSpacing }
val LocalHabitFlowComponents = staticCompositionLocalOf { DefaultHabitFlowComponents }
