package com.hk.habitflow.habit.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.hk.habitflow.habit.create.CreateHabitEvent
import com.hk.habitflow.habit.create.CreateHabitState
import com.hk.habitflow.habit.create.CreateHabitViewModel
import com.hk.habitflow.habit.create.DailyTargetType
import com.hk.habitflow.habit.create.defaultHabitIcons
import com.hk.habitflow.habit.model.HabitUi
import com.hk.habitflow.ui.theme.HabitFlowColors
import com.hk.habitflow.ui.theme.LocalHabitFlowSpacing
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun CreateHabitScreen(
    viewModel: CreateHabitViewModel,
    onBack: () -> Unit,
    onHabitCreated: (HabitUi) -> Unit,
    modifier: Modifier = Modifier
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    Column(modifier = modifier.fillMaxSize()) {
        CreateHabitHeader(onBackClick = {
            viewModel.onEvent(CreateHabitEvent.Back)
            onBack()
        })
        CreateHabitContent(
            state = state,
            onEvent = { event ->
                viewModel.onEvent(event)
                when (event) {
                    is CreateHabitEvent.Back -> onBack()
                    is CreateHabitEvent.Create -> buildHabitFromState(state)?.let { habit ->
                        onHabitCreated(habit)
                        onBack()
                    }
                    else -> { }
                }
            }
        )
    }
}

@Composable
private fun CreateHabitHeader(
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val spacing = LocalHabitFlowSpacing.current

    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(HabitFlowColors.Success)
            .padding(horizontal = spacing.screenHorizontal, vertical = spacing.medium),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        IconButton(
            onClick = onBackClick,
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .background(Color.White.copy(alpha = 0.2f))
        ) {
            Text(text = "←", style = MaterialTheme.typography.titleLarge, color = Color.White)
        }
        Text(
            text = "Create New Habit",
            style = MaterialTheme.typography.titleLarge,
            color = Color.White,
            modifier = Modifier.padding(start = spacing.small)
        )
    }
}

private fun buildHabitFromState(state: CreateHabitState): HabitUi? {
    val name = state.habitName.trim()
    if (name.isBlank()) return null
    val iconOption = defaultHabitIcons.getOrNull(state.selectedIconIndex) ?: defaultHabitIcons.first()
    val description = when (state.targetType) {
        DailyTargetType.Time -> "${state.targetValue} ${state.targetUnit} daily"
        DailyTargetType.Count -> "${state.targetValue} count daily"
    }
    val (progressTarget, progressCurrent) = when (state.targetType) {
        DailyTargetType.Time -> null to 0
        DailyTargetType.Count -> (state.targetValue.toIntOrNull() ?: 0).coerceAtLeast(0) to 0
    }
    return HabitUi(
        id = "",
        name = name,
        description = description,
        iconEmoji = iconOption.emoji,
        iconColor = iconOption.color,
        streakDays = 0,
        isCompletedToday = false,
        progressCurrent = progressCurrent,
        progressTarget = progressTarget
    )
}
