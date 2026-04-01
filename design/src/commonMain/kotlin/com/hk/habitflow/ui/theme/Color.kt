package com.hk.habitflow.ui.theme

import androidx.compose.material3.ColorScheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color

/**
 * Brand and semantic seed colors. Prefer [MaterialTheme.colorScheme] for generic UI.
 * Keep category / priority / accent tokens for lists and chips.
 */
object HabitFlowColors {
    val Primary = Color(0xFF6750A4)
    val PrimaryVariant = Color(0xFF7F67BE)
    val OnPrimary = Color(0xFFFFFFFF)
    val Secondary = Color(0xFF625B71)
    val OnSecondary = Color(0xFFFFFFFF)
    val Tertiary = Color(0xFF7D5260)
    val OnTertiary = Color(0xFFFFFFFF)
    val Error = Color(0xFFB3261E)
    val OnError = Color(0xFFFFFFFF)
    val Success = Color(0xFF22C55E)
    val SuccessContainer = Color(0xFFDCFCE7)
    val OnSuccess = Color(0xFFFFFFFF)
    val Focus = Color(0xFFF97316)
    val FocusContainer = Color(0xFFFFEDD5)
    val OnFocus = Color(0xFFFFFFFF)
    val Info = Color(0xFF3B82F6)
    val InfoContainer = Color(0xFFDBEAFE)
    val TextSecondary = Color(0xFF49454F)
    val TextPlaceholder = Color(0xFF94A3B8)
    val CategoryWork = Color(0xFFB91C1C)
    val CategoryPersonal = Color(0xFF6A0DAD)
    val CategoryHealth = Color(0xFF15803D)
    val CategoryShopping = Color(0xFF15803D)
    val CategoryLearning = Color(0xFFEAB308)
    val CategoryOther = Color(0xFFEC4899)
    val PriorityHigh = Color(0xFFB91C1C)
    val PriorityMedium = Color(0xFFEAB308)
    val PriorityLow = Color(0xFF22C55E)
}

fun habitFlowLightColorScheme(): ColorScheme = lightColorScheme(
    primary = HabitFlowColors.Primary,
    onPrimary = HabitFlowColors.OnPrimary,
    primaryContainer = Color(0xFFEADDFF),
    onPrimaryContainer = Color(0xFF21005D),
    secondary = HabitFlowColors.Secondary,
    onSecondary = HabitFlowColors.OnSecondary,
    secondaryContainer = Color(0xFFE8DEF8),
    onSecondaryContainer = Color(0xFF1D192B),
    tertiary = HabitFlowColors.Tertiary,
    onTertiary = HabitFlowColors.OnTertiary,
    tertiaryContainer = Color(0xFFFFD8E4),
    onTertiaryContainer = Color(0xFF31111D),
    error = HabitFlowColors.Error,
    onError = HabitFlowColors.OnError,
    errorContainer = Color(0xFFF9DEDC),
    onErrorContainer = Color(0xFF410E0B),
    background = Color(0xFFFEF7FF),
    onBackground = Color(0xFF1C1B1F),
    surface = Color(0xFFFEF7FF),
    onSurface = Color(0xFF1C1B1F),
    surfaceVariant = Color(0xFFE7E0EC),
    onSurfaceVariant = HabitFlowColors.TextSecondary,
    outline = Color(0xFF79747E),
    outlineVariant = Color(0xFFCAC4D0),
    surfaceContainerLowest = Color(0xFFFFFFFF),
    surfaceContainerLow = Color(0xFFF7F2FA),
    surfaceContainer = Color(0xFFF3EDF7),
    surfaceContainerHigh = Color(0xFFECE6F0),
    surfaceContainerHighest = Color(0xFFE6E0E9),
    inverseSurface = Color(0xFF313033),
    inverseOnSurface = Color(0xFFF4EFF4),
    inversePrimary = Color(0xFFD0BCFF),
    surfaceDim = Color(0xFFDED8E1),
    surfaceBright = Color(0xFFFEF7FF)
)

fun habitFlowDarkColorScheme(): ColorScheme = darkColorScheme(
    primary = Color(0xFFD0BCFF),
    onPrimary = Color(0xFF381E72),
    primaryContainer = Color(0xFF4F378B),
    onPrimaryContainer = Color(0xFFEADDFF),
    secondary = Color(0xFFCCC2DC),
    onSecondary = Color(0xFF332D41),
    secondaryContainer = Color(0xFF4A4458),
    onSecondaryContainer = Color(0xFFE8DEF8),
    tertiary = Color(0xFFEFB8C8),
    onTertiary = Color(0xFF492532),
    tertiaryContainer = Color(0xFF633B48),
    onTertiaryContainer = Color(0xFFFFD8E4),
    error = Color(0xFFF2B8B5),
    onError = Color(0xFF601410),
    errorContainer = Color(0xFF8C1D18),
    onErrorContainer = Color(0xFFF9DEDC),
    background = Color(0xFF1C1B1F),
    onBackground = Color(0xFFE6E1E5),
    surface = Color(0xFF1C1B1F),
    onSurface = Color(0xFFE6E1E5),
    surfaceVariant = Color(0xFF49454F),
    onSurfaceVariant = Color(0xFFCAC4D0),
    outline = Color(0xFF938F99),
    outlineVariant = Color(0xFF49454F),
    surfaceContainerLowest = Color(0xFF0F0D13),
    surfaceContainerLow = Color(0xFF1C1B1F),
    surfaceContainer = Color(0xFF211F26),
    surfaceContainerHigh = Color(0xFF2B2930),
    surfaceContainerHighest = Color(0xFF36343B),
    inverseSurface = Color(0xFFE6E1E5),
    inverseOnSurface = Color(0xFF313033),
    inversePrimary = Color(0xFF6750A4),
    surfaceDim = Color(0xFF141218),
    surfaceBright = Color(0xFF3B383E)
)
