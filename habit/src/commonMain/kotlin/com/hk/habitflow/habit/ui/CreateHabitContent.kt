package com.hk.habitflow.habit.ui

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.hk.habitflow.habit.create.CreateHabitEvent
import com.hk.habitflow.habit.create.CreateHabitState
import com.hk.habitflow.habit.create.DailyTargetType
import com.hk.habitflow.habit.create.HabitFrequency
import com.hk.habitflow.habit.create.defaultHabitIcons
import com.hk.habitflow.ui.theme.HabitFlowColors
import com.hk.habitflow.ui.theme.LocalHabitFlowComponents
import com.hk.habitflow.ui.theme.LocalHabitFlowSpacing

@Composable
fun CreateHabitContent(
    state: CreateHabitState,
    onEvent: (CreateHabitEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    val spacing = LocalHabitFlowSpacing.current
    val components = LocalHabitFlowComponents.current

    Column(
        modifier = modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
            .padding(spacing.screenHorizontal)
            .padding(bottom = spacing.extraLarge)
    ) {
        BuildYourStreakCard(modifier = Modifier.padding(bottom = spacing.medium))
        SectionLabel("Habit Name")
        OutlinedTextField(
            value = state.habitName,
            onValueChange = { onEvent(CreateHabitEvent.NameChange(it)) },
            placeholder = { Text("e.g., Morning Workout") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = spacing.medium),
            singleLine = true,
            shape = RoundedCornerShape(components.inputCornerRadius),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = MaterialTheme.colorScheme.outline,
                unfocusedBorderColor = MaterialTheme.colorScheme.outlineVariant
            )
        )
        SectionLabel("Choose Icon")
        Column(
            modifier = Modifier.fillMaxWidth().padding(bottom = spacing.medium),
            verticalArrangement = Arrangement.spacedBy(spacing.small)
        ) {
            defaultHabitIcons.chunked(6).forEachIndexed { rowIndex, rowIcons ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(spacing.small)
                ) {
                    rowIcons.forEachIndexed { colIndex, option ->
                        val globalIndex = rowIndex * 6 + colIndex
                        val selected = state.selectedIconIndex == globalIndex
                        Surface(
                            modifier = Modifier
                                .weight(1f)
                                .then(if (selected) Modifier.border(2.dp, option.color, RoundedCornerShape(components.inputCornerRadius)) else Modifier),
                            shape = RoundedCornerShape(components.inputCornerRadius),
                            color = option.color.copy(alpha = 0.2f),
                            onClick = { onEvent(CreateHabitEvent.IconSelect(globalIndex)) }
                        ) {
                            androidx.compose.foundation.layout.Box(
                                modifier = Modifier.size(48.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(text = option.emoji, style = MaterialTheme.typography.titleMedium)
                            }
                        }
                    }
                }
            }
        }
        SectionLabel("Frequency")
        Row(
            modifier = Modifier.fillMaxWidth().padding(bottom = spacing.medium),
            horizontalArrangement = Arrangement.spacedBy(spacing.small)
        ) {
            FrequencyChip(
                label = "Daily",
                selected = state.frequency == HabitFrequency.Daily,
                onClick = { onEvent(CreateHabitEvent.FrequencySelect(HabitFrequency.Daily)) },
                modifier = Modifier.weight(1f)
            )
            FrequencyChip(
                label = "Custom Days",
                selected = state.frequency == HabitFrequency.CustomDays,
                onClick = { onEvent(CreateHabitEvent.FrequencySelect(HabitFrequency.CustomDays)) },
                modifier = Modifier.weight(1f)
            )
        }
        SectionLabel("Daily Target")
        Row(
            modifier = Modifier.fillMaxWidth().padding(bottom = spacing.small),
            horizontalArrangement = Arrangement.spacedBy(spacing.small)
        ) {
            TargetTypeChip(
                label = "Time",
                icon = "🕐",
                selected = state.targetType == DailyTargetType.Time,
                onClick = { onEvent(CreateHabitEvent.TargetTypeSelect(DailyTargetType.Time)) },
                modifier = Modifier.weight(1f)
            )
            TargetTypeChip(
                label = "# Count",
                icon = "#",
                selected = state.targetType == DailyTargetType.Count,
                onClick = { onEvent(CreateHabitEvent.TargetTypeSelect(DailyTargetType.Count)) },
                modifier = Modifier.weight(1f)
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth().padding(bottom = spacing.medium),
            horizontalArrangement = Arrangement.spacedBy(spacing.small)
        ) {
            OutlinedTextField(
                value = state.targetValue,
                onValueChange = { onEvent(CreateHabitEvent.TargetValueChange(it)) },
                modifier = Modifier.weight(1f),
                singleLine = true,
                shape = RoundedCornerShape(components.inputCornerRadius),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = MaterialTheme.colorScheme.outline,
                    unfocusedBorderColor = MaterialTheme.colorScheme.outlineVariant
                )
            )
            OutlinedTextField(
                value = state.targetUnit,
                onValueChange = { onEvent(CreateHabitEvent.TargetUnitChange(it)) },
                modifier = Modifier.weight(1f),
                singleLine = true,
                shape = RoundedCornerShape(components.inputCornerRadius),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = MaterialTheme.colorScheme.outline,
                    unfocusedBorderColor = MaterialTheme.colorScheme.outlineVariant
                )
            )
        }
        SectionLabel("Daily Reminder")
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = spacing.medium)
                .border(1.dp, MaterialTheme.colorScheme.outlineVariant, RoundedCornerShape(components.inputCornerRadius)),
            shape = RoundedCornerShape(components.inputCornerRadius),
            color = HabitFlowColors.Success.copy(alpha = 0.08f)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(spacing.medium),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(spacing.small)) {
                    Text(text = "🔔", style = MaterialTheme.typography.titleMedium)
                    Column {
                        Text("Set Reminder", style = MaterialTheme.typography.titleSmall, color = MaterialTheme.colorScheme.onSurface)
                        Text("Not set", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                }
                Switch(
                    checked = state.reminderEnabled,
                    onCheckedChange = { onEvent(CreateHabitEvent.ReminderChange(it)) },
                    colors = SwitchDefaults.colors(checkedThumbColor = HabitFlowColors.Success)
                )
            }
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(spacing.medium)
        ) {
            Button(
                onClick = { onEvent(CreateHabitEvent.Back) },
                modifier = Modifier.weight(1f),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
                shape = RoundedCornerShape(components.buttonCornerRadius)
            ) {
                Text("Cancel", color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
            Button(
                onClick = { onEvent(CreateHabitEvent.Create) },
                modifier = Modifier.weight(1f),
                colors = ButtonDefaults.buttonColors(containerColor = HabitFlowColors.Success),
                shape = RoundedCornerShape(components.buttonCornerRadius)
            ) {
                Text("Create Habit", color = Color.White)
            }
        }
    }
}

@Composable
private fun BuildYourStreakCard(modifier: Modifier = Modifier) {
    val spacing = LocalHabitFlowSpacing.current
        Surface(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        color = Color(0xFFFFF7ED)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(spacing.cardPadding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "🔥", style = MaterialTheme.typography.displaySmall)
            Text(
                text = "Build Your Streak",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = "Complete this habit daily to unlock rewards",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Row(
                modifier = Modifier.padding(top = spacing.small),
                horizontalArrangement = Arrangement.spacedBy(spacing.tiny),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("7 days 🏆", style = MaterialTheme.typography.labelMedium, color = HabitFlowColors.Focus)
                Text("•", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                Text("30 days 🏅", style = MaterialTheme.typography.labelMedium, color = HabitFlowColors.Focus)
                Text("•", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                Text("100 days 👑", style = MaterialTheme.typography.labelMedium, color = HabitFlowColors.Focus)
            }
        }
    }
}

@Composable
private fun SectionLabel(text: String) {
    Text(
        text = text,
        style = MaterialTheme.typography.titleSmall,
        color = MaterialTheme.colorScheme.onSurface,
        modifier = Modifier.padding(bottom = 4.dp)
    )
}

@Composable
private fun FrequencyChip(
    label: String,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier,
        onClick = onClick,
        shape = RoundedCornerShape(8.dp),
        color = if (selected) HabitFlowColors.Success else MaterialTheme.colorScheme.surfaceVariant
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelLarge,
            color = if (selected) Color.White else MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(vertical = 12.dp)
        )
    }
}

@Composable
private fun TargetTypeChip(
    label: String,
    icon: String,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier,
        onClick = onClick,
        shape = RoundedCornerShape(8.dp),
        color = if (selected) HabitFlowColors.Success else MaterialTheme.colorScheme.surfaceVariant
    ) {
        Row(
            modifier = Modifier.padding(vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(text = icon, style = MaterialTheme.typography.labelLarge)
            Text(
                text = label,
                style = MaterialTheme.typography.labelLarge,
                color = if (selected) Color.White else MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}
