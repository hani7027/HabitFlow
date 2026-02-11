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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.hk.habitflow.ui.theme.HabitFlowColors
import com.hk.habitflow.ui.theme.LocalHabitFlowSpacing

@Composable
fun TasksHeader(
    dateText: String,
    onSearchClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val spacing = LocalHabitFlowSpacing.current
    val gradient = Brush.verticalGradient(
        colors = listOf(
            HabitFlowColors.Primary,
            HabitFlowColors.PrimaryVariant.copy(alpha = 0.9f),
            Color(0xFFE9D5FF)
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
                color = Color.White
            )
            Text(
                text = dateText,
                style = MaterialTheme.typography.bodyMedium,
                color = Color.White.copy(alpha = 0.9f)
            )
        }
        IconButton(
            onClick = onSearchClick,
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .background(Color.White.copy(alpha = 0.2f))
        ) {
            Text(
                text = "⌕",
                style = MaterialTheme.typography.titleMedium,
                color = Color.White
            )
        }
    }
}
