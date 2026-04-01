package com.hk.habitflow.task.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.unit.dp
import com.hk.habitflow.ui.theme.LocalHabitFlowSpacing

@Composable
fun TasksHeader(
    dateText: String,
    onSearchClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val spacing = LocalHabitFlowSpacing.current
    val scheme = MaterialTheme.colorScheme
    val gradient = Brush.verticalGradient(
        colors = listOf(
            scheme.primary,
            scheme.primaryContainer,
            scheme.surfaceContainerHigh
        )
    )

    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(gradient)
            .padding(horizontal = spacing.screenHorizontal, vertical = spacing.large),
        verticalAlignment = Alignment.CenterVertically
    ) {
        androidx.compose.foundation.layout.Column(modifier = Modifier.weight(1f)) {
            Text(
                text = "Tasks",
                style = MaterialTheme.typography.headlineMedium,
                color = scheme.onPrimary
            )
            Text(
                text = dateText,
                style = MaterialTheme.typography.bodyMedium,
                color = scheme.onPrimaryContainer
            )
        }
        IconButton(
            onClick = onSearchClick,
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .background(scheme.onPrimary.copy(alpha = 0.2f))
        ) {
            Text(
                text = "⌕",
                style = MaterialTheme.typography.titleMedium,
                color = scheme.onPrimary
            )
        }
    }
}
