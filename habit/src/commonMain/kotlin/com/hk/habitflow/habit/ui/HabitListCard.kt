package com.hk.habitflow.habit.ui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.hk.habitflow.habit.model.HabitUi
import com.hk.habitflow.ui.theme.HabitFlowColors
import com.hk.habitflow.ui.theme.LocalHabitFlowComponents
import com.hk.habitflow.ui.theme.LocalHabitFlowSpacing

@Composable
fun HabitListCard(
    habit: HabitUi,
    onCompleteClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val spacing = LocalHabitFlowSpacing.current
    val components = LocalHabitFlowComponents.current

    Surface(
        modifier = modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.medium,
        color = MaterialTheme.colorScheme.surface,
        tonalElevation = 2.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(spacing.cardPadding),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(spacing.medium)
        ) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(MaterialTheme.shapes.medium)
                    .background(habit.iconColor.copy(alpha = 0.2f)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = habit.iconEmoji,
                    style = MaterialTheme.typography.titleLarge
                )
            }
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = habit.name,
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = habit.description,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Surface(
                    modifier = Modifier.padding(top = spacing.tiny),
                    shape = MaterialTheme.shapes.small,
                    color = HabitFlowColors.FocusContainer
                ) {
                    Text(
                        text = "🔥 ${habit.streakDays} day streak",
                        style = MaterialTheme.typography.labelSmall,
                        color = HabitFlowColors.Focus,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }
            }
            CompletionIndicator(
                habit = habit,
                onClick = onCompleteClick
            )
        }
    }
}

@Composable
private fun CompletionIndicator(
    habit: HabitUi,
    onClick: () -> Unit
) {
    val indicatorSize = 48.dp

    Box(
        modifier = Modifier
            .size(indicatorSize)
            .clip(MaterialTheme.shapes.medium)
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        if (habit.isCountable) {
            val progress = habit.progressFraction
            val ringColor = HabitFlowColors.Success
            val ringBg = MaterialTheme.colorScheme.surfaceVariant
            Canvas(modifier = Modifier.size(indicatorSize)) {
                val strokeWidth = 4.dp.toPx()
                val radius = (this.size.minDimension - strokeWidth) / 2f
                val center = Offset(this.size.width / 2f, this.size.height / 2f)
                drawCircle(
                    color = ringBg,
                    radius = radius,
                    center = center,
                    style = Stroke(width = strokeWidth, cap = StrokeCap.Round)
                )
                drawArc(
                    color = ringColor,
                    startAngle = -90f,
                    sweepAngle = 360f * progress,
                    useCenter = false,
                    topLeft = Offset(center.x - radius, center.y - radius),
                    size = Size(radius * 2, radius * 2),
                    style = Stroke(width = strokeWidth, cap = StrokeCap.Round)
                )
            }
            Text(
                text = "${habit.progressCurrent}/${habit.progressTarget!!}",
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurface
            )
        } else {
            val bg = if (habit.isCompletedToday) habit.iconColor else MaterialTheme.colorScheme.surfaceVariant
            Box(
                modifier = Modifier
                    .size(indicatorSize)
                    .clip(MaterialTheme.shapes.medium)
                    .background(bg),
                contentAlignment = Alignment.Center
            ) {
                if (habit.isCompletedToday) {
                    Text(text = "✓", style = MaterialTheme.typography.titleMedium, color = Color.White)
                } else {
                    Text(text = "+", style = MaterialTheme.typography.titleMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
            }
        }
    }
}
