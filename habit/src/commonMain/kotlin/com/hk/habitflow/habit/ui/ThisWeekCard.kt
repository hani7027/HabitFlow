package com.hk.habitflow.habit.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.hk.habitflow.habit.WeekDayStatus
import com.hk.habitflow.ui.theme.HabitFlowColors
import com.hk.habitflow.ui.theme.LocalHabitFlowSpacing

private val dayLabels = listOf("Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun")

@Composable
fun ThisWeekCard(
    weekDayStatuses: List<WeekDayStatus>,
    modifier: Modifier = Modifier
) {
    val spacing = LocalHabitFlowSpacing.current
    val statuses = weekDayStatuses.take(7)
        .let { list -> list + List((7 - list.size).coerceAtLeast(0)) { WeekDayStatus.Incomplete } }
        .take(7)

    Surface(
        modifier = modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.extraLarge,
        color = Color(0xFFFFF7ED)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(spacing.cardPadding)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(spacing.small)
            ) {
                Text(text = "🏆", style = MaterialTheme.typography.titleLarge)
                Text(
                    text = "This Week",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = spacing.medium),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                dayLabels.forEachIndexed { index, label ->
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(spacing.tiny)
                    ) {
                        Text(
                            text = label,
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        DayIndicator(status = statuses.getOrElse(index) { WeekDayStatus.Incomplete })
                    }
                }
            }
        }
    }
}

@Composable
private fun DayIndicator(
    status: WeekDayStatus,
    modifier: Modifier = Modifier
) {
    val (backgroundColor, showCheckmark) = when (status) {
        WeekDayStatus.Completed -> HabitFlowColors.Success to true
        WeekDayStatus.Partial -> HabitFlowColors.Focus to true
        WeekDayStatus.Incomplete -> MaterialTheme.colorScheme.surfaceVariant to false
    }
    Box(
        modifier = modifier
            .size(32.dp)
            .clip(MaterialTheme.shapes.medium)
            .background(backgroundColor),
        contentAlignment = Alignment.Center
    ) {
        if (showCheckmark) {
            Text(
                text = "✓",
                style = MaterialTheme.typography.labelMedium,
                color = Color.White
            )
        }
    }
}
