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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.hk.habitflow.habit.create.CreateHabitEffect
import com.hk.habitflow.habit.create.CreateHabitEvent
import com.hk.habitflow.habit.create.CreateHabitViewModel
import com.hk.habitflow.ui.theme.HabitFlowColors
import com.hk.habitflow.ui.theme.LocalHabitFlowSpacing
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun CreateHabitScreen(
    viewModel: CreateHabitViewModel,
    onBack: () -> Unit,
    onHabitCreated: (com.hk.habitflow.habit.model.HabitUi) -> Unit = {},
    modifier: Modifier = Modifier
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                CreateHabitEffect.Dismiss -> onBack()
            }
        }
    }

    Column(modifier = modifier.fillMaxSize()) {
        CreateHabitHeader(onBackClick = { viewModel.onEvent(CreateHabitEvent.Back) })
        CreateHabitContent(
            state = state,
            onEvent = viewModel::onEvent
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
