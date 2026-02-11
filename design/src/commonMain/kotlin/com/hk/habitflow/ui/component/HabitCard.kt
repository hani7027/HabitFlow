package com.hk.habitflow.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.hk.habitflow.ui.theme.LocalHabitFlowSpacing

data class HabitCardUi(
    val id: String,
    val name: String,
    val progressText: String,
    val streakDays: Int,
    val cardColor: Color
)

@Composable
fun HabitCard(
    habit: HabitCardUi,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val spacing = LocalHabitFlowSpacing.current

    Box(
        modifier = modifier
            .width(140.dp)
            .height(160.dp)
            .clip(MaterialTheme.shapes.extraLarge)
            .background(habit.cardColor)
            .clickable(onClick = onClick)
            .padding(spacing.medium)
    ) {
        Column(modifier = Modifier.fillMaxHeight()) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(MaterialTheme.shapes.medium)
                    .background(Color.White.copy(alpha = 0.3f)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = habit.name.take(1).uppercase(),
                    style = MaterialTheme.typography.titleMedium,
                    color = Color.White
                )
            }
            Spacer(modifier = Modifier.height(spacing.small))
            Text(
                text = habit.name,
                style = MaterialTheme.typography.titleSmall,
                color = Color.White
            )
            Text(
                text = habit.progressText,
                style = MaterialTheme.typography.bodySmall,
                color = Color.White.copy(alpha = 0.9f)
            )
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = "🔥 ${habit.streakDays} days",
                style = MaterialTheme.typography.labelSmall,
                color = Color.White.copy(alpha = 0.9f)
            )
        }
    }
}
