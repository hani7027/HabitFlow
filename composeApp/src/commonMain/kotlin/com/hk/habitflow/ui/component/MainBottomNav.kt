package com.hk.habitflow.ui.component

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.hk.habitflow.ui.navigation.MainTab

/**
 * Bottom navigation using M3 [NavigationBar]. Icons are emoji (no JetBrains `material-icons-*`
 * dependency — those artifacts are not reliably published for all Compose versions).
 */
@Composable
fun MainBottomNav(
    currentTab: MainTab,
    onTabSelected: (MainTab) -> Unit,
    modifier: Modifier = Modifier
) {
    val colors = MaterialTheme.colorScheme
    NavigationBar(
        modifier = modifier,
        containerColor = colors.surfaceContainer,
        contentColor = colors.onSurface
    ) {
        NavigationBarItem(
            selected = currentTab == MainTab.Home,
            onClick = { onTabSelected(MainTab.Home) },
            icon = {
                NavBarGlyph(
                    glyph = "⌂",
                    selected = currentTab == MainTab.Home
                )
            },
            label = { Text("Home") },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = colors.primary,
                selectedTextColor = colors.primary,
                indicatorColor = colors.primaryContainer,
                unselectedIconColor = colors.onSurfaceVariant,
                unselectedTextColor = colors.onSurfaceVariant
            )
        )
        NavigationBarItem(
            selected = currentTab == MainTab.Tasks,
            onClick = { onTabSelected(MainTab.Tasks) },
            icon = {
                NavBarGlyph(
                    glyph = "☑",
                    selected = currentTab == MainTab.Tasks
                )
            },
            label = { Text("Tasks") },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = colors.primary,
                selectedTextColor = colors.primary,
                indicatorColor = colors.primaryContainer,
                unselectedIconColor = colors.onSurfaceVariant,
                unselectedTextColor = colors.onSurfaceVariant
            )
        )
        NavigationBarItem(
            selected = currentTab == MainTab.Habits,
            onClick = { onTabSelected(MainTab.Habits) },
            icon = {
                NavBarGlyph(
                    glyph = "↻",
                    selected = currentTab == MainTab.Habits
                )
            },
            label = { Text("Habits") },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = colors.primary,
                selectedTextColor = colors.primary,
                indicatorColor = colors.primaryContainer,
                unselectedIconColor = colors.onSurfaceVariant,
                unselectedTextColor = colors.onSurfaceVariant
            )
        )
    }
}

@Composable
private fun NavBarGlyph(
    glyph: String,
    selected: Boolean
) {
    val colors = MaterialTheme.colorScheme
    Text(
        text = glyph,
        style = MaterialTheme.typography.titleLarge,
        color = if (selected) colors.primary else colors.onSurfaceVariant
    )
}
