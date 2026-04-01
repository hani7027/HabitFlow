package com.hk.habitflow.ui.theme

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/** 8dp-based spacing grid. */
data class HabitFlowSpacing(
    val tiny: Dp,
    val small: Dp,
    val medium: Dp,
    val large: Dp,
    val extraLarge: Dp,
    val screenHorizontal: Dp,
    val cardPadding: Dp,
    val listItemVertical: Dp,
    val inputPaddingVertical: Dp,
    val inputPaddingHorizontal: Dp
)

val DefaultHabitFlowSpacing = HabitFlowSpacing(
    tiny = 4.dp,
    small = 8.dp,
    medium = 16.dp,
    large = 24.dp,
    extraLarge = 32.dp,
    screenHorizontal = 16.dp,
    cardPadding = 16.dp,
    listItemVertical = 12.dp,
    inputPaddingVertical = 12.dp,
    inputPaddingHorizontal = 16.dp
)

data class HabitFlowComponents(
    val cornerRadiusSmall: Dp,
    val cornerRadiusMedium: Dp,
    val cornerRadiusLarge: Dp,
    val cornerRadiusExtraLarge: Dp,
    val cardElevation: Dp,
    val cardCornerRadius: Dp,
    val inputCornerRadius: Dp,
    val inputBorderWidth: Dp,
    val inputMinHeight: Dp,
    val buttonCornerRadius: Dp,
    val chipCornerRadius: Dp,
    val fabSize: Dp,
    val fabIconSize: Dp
)

val DefaultHabitFlowComponents = HabitFlowComponents(
    cornerRadiusSmall = 4.dp,
    cornerRadiusMedium = 8.dp,
    cornerRadiusLarge = 12.dp,
    cornerRadiusExtraLarge = 16.dp,
    cardElevation = 1.dp,
    cardCornerRadius = 16.dp,
    inputCornerRadius = 8.dp,
    inputBorderWidth = 1.dp,
    inputMinHeight = 48.dp,
    buttonCornerRadius = 12.dp,
    chipCornerRadius = 8.dp,
    fabSize = 56.dp,
    fabIconSize = 24.dp
)
