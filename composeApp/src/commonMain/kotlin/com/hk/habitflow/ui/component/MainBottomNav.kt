package com.hk.habitflow.ui.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.hk.habitflow.ui.navigation.MainTab
import com.hk.habitflow.ui.theme.HabitFlowColors
import com.hk.habitflow.ui.theme.LocalHabitFlowSpacing

@Composable
fun MainBottomNav(
    currentTab: MainTab,
    onTabSelected: (MainTab) -> Unit,
    modifier: Modifier = Modifier
) {
    val spacing = LocalHabitFlowSpacing.current

    Surface(
        modifier = modifier
            .fillMaxWidth()
            .height(64.dp)
            .clip(MaterialTheme.shapes.large),
        color = MaterialTheme.colorScheme.surfaceVariant,
        tonalElevation = 8.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(spacing.medium),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            NavItem(
                label = "Home",
                selected = currentTab == MainTab.Home,
                onClick = { onTabSelected(MainTab.Home) }
            )
            NavItem(
                label = "Tasks",
                selected = currentTab == MainTab.Tasks,
                onClick = { onTabSelected(MainTab.Tasks) }
            )
            NavItem(
                label = "Habits",
                selected = currentTab == MainTab.Habits,
                onClick = { onTabSelected(MainTab.Habits) }
            )
        }
    }
}

@Composable
private fun NavItem(
    label: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    val color = if (selected) HabitFlowColors.Primary else MaterialTheme.colorScheme.onSurfaceVariant
    Column(
        modifier = Modifier.clickable(onClick = onClick),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelMedium,
            color = color
        )
    }
}
