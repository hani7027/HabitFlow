package com.hk.habitflow.ui.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * Single source of truth for all visual attributes.
 * Aligned with HabitFlow hi-fi design: rounded corners, card-based layout,
 * primary purple, success green, focus orange, clear typography.
 * Screens use MaterialTheme + LocalHabitFlowSpacing + LocalHabitFlowComponents.
 */

// ---------- Colors (from HabitFlow design) ----------

object HabitFlowColors {
    val Primary = Color(0xFF6A0DAD)
    val PrimaryVariant = Color(0xFF7B2CBF)
    val OnPrimary = Color.White
    val Success = Color(0xFF22C55E)
    val SuccessContainer = Color(0xFFDCFCE7)
    val OnSuccess = Color.White
    val Focus = Color(0xFFF97316)
    val FocusContainer = Color(0xFFFFEDD5)
    val OnFocus = Color.White
    val Secondary = Color(0xFF0D9488)
    val SecondaryContainer = Color(0xFFCCFBF1)
    val OnSecondary = Color.White
    val Info = Color(0xFF3B82F6)
    val InfoContainer = Color(0xFFDBEAFE)
    val BackgroundLight = Color(0xFFF8FAFC)
    val SurfaceLight = Color.White
    val SurfaceVariantLight = Color(0xFFF1F5F9)
    val OnBackgroundLight = Color(0xFF1E293B)
    val OnSurfaceLight = Color(0xFF1E293B)
    val OutlineLight = Color(0xFFE2E8F0)
    val OutlineVariantLight = Color(0xFFCBD5E1)
    val TextPrimary = Color(0xFF1E293B)
    val TextSecondary = Color(0xFF64748B)
    val TextPlaceholder = Color(0xFF94A3B8)
    val TextOnPrimary = Color.White
    val Error = Color(0xFFB3261E)
    val OnError = Color.White
    val CategoryWork = Color(0xFFB91C1C)
    val CategoryPersonal = Color(0xFF6A0DAD)
    val CategoryHealth = Color(0xFF15803D)
    val PriorityHigh = Color(0xFFB91C1C)
    val PriorityMedium = Color(0xFFEAB308)
    val PriorityLow = Color(0xFF22C55E)
    val BackgroundDark = Color(0xFF0F172A)
    val SurfaceDark = Color(0xFF1E293B)
    val OnBackgroundDark = Color(0xFFF1F5F9)
    val OnSurfaceDark = Color(0xFFF1F5F9)
    val OutlineDark = Color(0xFF475569)
}

fun habitFlowLightColorScheme(): ColorScheme = lightColorScheme(
    primary = HabitFlowColors.Primary,
    onPrimary = HabitFlowColors.OnPrimary,
    primaryContainer = HabitFlowColors.PrimaryVariant.copy(alpha = 0.2f),
    onPrimaryContainer = HabitFlowColors.Primary,
    secondary = HabitFlowColors.Secondary,
    onSecondary = HabitFlowColors.OnSecondary,
    secondaryContainer = HabitFlowColors.SecondaryContainer,
    onSecondaryContainer = HabitFlowColors.Secondary,
    tertiary = HabitFlowColors.Focus,
    onTertiary = HabitFlowColors.OnFocus,
    tertiaryContainer = HabitFlowColors.FocusContainer,
    onTertiaryContainer = HabitFlowColors.Focus,
    background = HabitFlowColors.BackgroundLight,
    onBackground = HabitFlowColors.OnBackgroundLight,
    surface = HabitFlowColors.SurfaceLight,
    onSurface = HabitFlowColors.OnSurfaceLight,
    surfaceVariant = HabitFlowColors.SurfaceVariantLight,
    onSurfaceVariant = HabitFlowColors.TextSecondary,
    outline = HabitFlowColors.OutlineLight,
    outlineVariant = HabitFlowColors.OutlineVariantLight,
    error = HabitFlowColors.Error,
    onError = HabitFlowColors.OnError
)

fun habitFlowDarkColorScheme(): ColorScheme = darkColorScheme(
    primary = HabitFlowColors.Primary,
    onPrimary = HabitFlowColors.OnPrimary,
    primaryContainer = HabitFlowColors.PrimaryVariant.copy(alpha = 0.3f),
    onPrimaryContainer = HabitFlowColors.OnPrimary,
    secondary = HabitFlowColors.Secondary,
    onSecondary = HabitFlowColors.OnSecondary,
    secondaryContainer = HabitFlowColors.Secondary.copy(alpha = 0.3f),
    onSecondaryContainer = HabitFlowColors.SecondaryContainer,
    tertiary = HabitFlowColors.Focus,
    onTertiary = HabitFlowColors.OnFocus,
    tertiaryContainer = HabitFlowColors.Focus.copy(alpha = 0.3f),
    onTertiaryContainer = HabitFlowColors.FocusContainer,
    background = HabitFlowColors.BackgroundDark,
    onBackground = HabitFlowColors.OnBackgroundDark,
    surface = HabitFlowColors.SurfaceDark,
    onSurface = HabitFlowColors.OnSurfaceDark,
    surfaceVariant = HabitFlowColors.SurfaceDark,
    onSurfaceVariant = HabitFlowColors.OnSurfaceDark,
    outline = HabitFlowColors.OutlineDark,
    error = HabitFlowColors.Error,
    onError = HabitFlowColors.OnError
)

val HabitFlowTypography = Typography(
    displayLarge = TextStyle(fontWeight = FontWeight.Normal, fontSize = 57.sp, lineHeight = 64.sp, letterSpacing = (-0.25).sp),
    displayMedium = TextStyle(fontWeight = FontWeight.Normal, fontSize = 45.sp, lineHeight = 52.sp),
    displaySmall = TextStyle(fontWeight = FontWeight.Normal, fontSize = 36.sp, lineHeight = 44.sp),
    headlineLarge = TextStyle(fontWeight = FontWeight.Bold, fontSize = 28.sp, lineHeight = 36.sp),
    headlineMedium = TextStyle(fontWeight = FontWeight.Bold, fontSize = 24.sp, lineHeight = 32.sp),
    headlineSmall = TextStyle(fontWeight = FontWeight.SemiBold, fontSize = 22.sp, lineHeight = 28.sp),
    titleLarge = TextStyle(fontWeight = FontWeight.SemiBold, fontSize = 20.sp, lineHeight = 28.sp),
    titleMedium = TextStyle(fontWeight = FontWeight.Medium, fontSize = 16.sp, lineHeight = 24.sp, letterSpacing = 0.15.sp),
    titleSmall = TextStyle(fontWeight = FontWeight.Medium, fontSize = 14.sp, lineHeight = 20.sp, letterSpacing = 0.1.sp),
    bodyLarge = TextStyle(fontWeight = FontWeight.Normal, fontSize = 16.sp, lineHeight = 24.sp, letterSpacing = 0.5.sp),
    bodyMedium = TextStyle(fontWeight = FontWeight.Normal, fontSize = 14.sp, lineHeight = 20.sp, letterSpacing = 0.25.sp),
    bodySmall = TextStyle(fontWeight = FontWeight.Normal, fontSize = 12.sp, lineHeight = 16.sp, letterSpacing = 0.4.sp),
    labelLarge = TextStyle(fontWeight = FontWeight.Medium, fontSize = 14.sp, lineHeight = 20.sp, letterSpacing = 0.1.sp),
    labelMedium = TextStyle(fontWeight = FontWeight.Medium, fontSize = 12.sp, lineHeight = 16.sp, letterSpacing = 0.5.sp),
    labelSmall = TextStyle(fontWeight = FontWeight.Medium, fontSize = 11.sp, lineHeight = 16.sp, letterSpacing = 0.5.sp)
)

private val CornerRadiusSmall = 4.dp
private val CornerRadiusMedium = 8.dp
private val CornerRadiusLarge = 12.dp
private val CornerRadiusExtraLarge = 16.dp

val HabitFlowShapes = Shapes(
    extraSmall = RoundedCornerShape(CornerRadiusSmall),
    small = RoundedCornerShape(CornerRadiusMedium),
    medium = RoundedCornerShape(CornerRadiusLarge),
    large = RoundedCornerShape(CornerRadiusLarge),
    extraLarge = RoundedCornerShape(CornerRadiusExtraLarge)
)

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
    cardElevation = 2.dp,
    cardCornerRadius = 16.dp,
    inputCornerRadius = 8.dp,
    inputBorderWidth = 1.dp,
    inputMinHeight = 48.dp,
    buttonCornerRadius = 12.dp,
    chipCornerRadius = 8.dp,
    fabSize = 56.dp,
    fabIconSize = 24.dp
)
