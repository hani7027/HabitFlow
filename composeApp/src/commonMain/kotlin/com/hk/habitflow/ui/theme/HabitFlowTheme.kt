package com.hk.habitflow.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf

/**
 * Single theme entry for the app. All styles are driven from [DesignTokens]
 * (aligned with HabitFlow hi-fi design).
 * Use MaterialTheme.colorScheme, MaterialTheme.typography, MaterialTheme.shapes.
 * Use LocalHabitFlowSpacing.current for padding/margins.
 * Use LocalHabitFlowComponents.current for input, button, card, FAB tokens.
 */
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
