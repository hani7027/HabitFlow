package com.hk.habitflow.task.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.hk.habitflow.task.model.TaskCategory
import com.hk.habitflow.task.model.TaskPriority
import com.hk.habitflow.ui.theme.HabitFlowColors
import com.hk.habitflow.ui.theme.LocalHabitFlowComponents
import com.hk.habitflow.ui.theme.LocalHabitFlowSpacing

@Composable
fun AddTaskSheet(
    title: String,
    description: String,
    selectedCategory: TaskCategory?,
    selectedPriority: TaskPriority?,
    dueDate: String,
    dueTime: String,
    reminderEnabled: Boolean,
    onTitleChange: (String) -> Unit,
    onDescriptionChange: (String) -> Unit,
    onCategorySelect: (TaskCategory) -> Unit,
    onPrioritySelect: (TaskPriority) -> Unit,
    onDueDateChange: (String) -> Unit,
    onDueTimeChange: (String) -> Unit,
    onReminderChange: (Boolean) -> Unit,
    onDismiss: () -> Unit,
    onSave: () -> Unit,
    modifier: Modifier = Modifier
) {
    val spacing = LocalHabitFlowSpacing.current
    val components = LocalHabitFlowComponents.current

    Surface(
        modifier = modifier
            .fillMaxWidth()
            .height(560.dp),
        shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
        color = MaterialTheme.colorScheme.surface
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
                .padding(spacing.screenHorizontal)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Add New Task",
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onSurface
                )
                IconButton(onClick = onDismiss) {
                    Text(
                        text = "×",
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            }
            HorizontalDivider(modifier = Modifier.padding(vertical = spacing.small))
            OutlinedTextField(
                value = title,
                onValueChange = onTitleChange,
                label = { Text("Task Title *") },
                placeholder = { Text("What do you need to do?") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                shape = RoundedCornerShape(components.inputCornerRadius),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = MaterialTheme.colorScheme.outline,
                    unfocusedBorderColor = MaterialTheme.colorScheme.outlineVariant
                )
            )
            OutlinedTextField(
                value = description,
                onValueChange = onDescriptionChange,
                label = { Text("Description (Optional)") },
                placeholder = { Text("Add more details...") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = spacing.medium)
                    .height(80.dp),
                maxLines = 3,
                shape = RoundedCornerShape(components.inputCornerRadius),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = MaterialTheme.colorScheme.outline,
                    unfocusedBorderColor = MaterialTheme.colorScheme.outlineVariant
                )
            )
            Text(
                text = "Category *",
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.padding(top = spacing.medium)
            )
            Column(
                modifier = Modifier.fillMaxWidth().padding(top = spacing.small),
                verticalArrangement = Arrangement.spacedBy(spacing.small)
            ) {
                TaskCategory.entries.chunked(3).forEach { row ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(spacing.small)
                    ) {
                        row.forEach { cat ->
                            CategoryChip(
                                category = cat,
                                selected = selectedCategory == cat,
                                onClick = { onCategorySelect(cat) },
                                modifier = Modifier.weight(1f)
                            )
                        }
                    }
                }
            }
            Text(
                text = "Priority *",
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.padding(top = spacing.medium)
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = spacing.small),
                horizontalArrangement = Arrangement.spacedBy(spacing.small)
            ) {
                TaskPriority.entries.forEach { pri ->
                    PriorityChip(
                        priority = pri,
                        selected = selectedPriority == pri,
                        onClick = { onPrioritySelect(pri) },
                        modifier = Modifier.weight(1f)
                    )
                }
            }
            Text(
                text = "Due Date & Time",
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.padding(top = spacing.medium)
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = spacing.small),
                horizontalArrangement = Arrangement.spacedBy(spacing.small)
            ) {
                OutlinedTextField(
                    value = dueDate,
                    onValueChange = onDueDateChange,
                    placeholder = { Text("dd/mm/yyyy") },
                    modifier = Modifier.weight(1f),
                    singleLine = true,
                    shape = RoundedCornerShape(components.inputCornerRadius),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = MaterialTheme.colorScheme.outline,
                        unfocusedBorderColor = MaterialTheme.colorScheme.outlineVariant
                    )
                )
                OutlinedTextField(
                    value = dueTime,
                    onValueChange = onDueTimeChange,
                    placeholder = { Text("--:--") },
                    modifier = Modifier.weight(1f),
                    singleLine = true,
                    shape = RoundedCornerShape(components.inputCornerRadius),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = MaterialTheme.colorScheme.outline,
                        unfocusedBorderColor = MaterialTheme.colorScheme.outlineVariant
                    )
                )
            }
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = spacing.medium)
                    .clip(RoundedCornerShape(components.inputCornerRadius))
                    .clickable { onReminderChange(!reminderEnabled) },
                color = HabitFlowColors.Primary.copy(alpha = 0.1f)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(spacing.medium),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column {
                        Text(
                            text = "Set Reminder",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Text(
                            text = "Get notified before due time",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                    Switch(
                        checked = reminderEnabled,
                        onCheckedChange = onReminderChange,
                        colors = SwitchDefaults.colors(checkedThumbColor = HabitFlowColors.Primary)
                    )
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = spacing.large, bottom = spacing.extraLarge),
                horizontalArrangement = Arrangement.spacedBy(spacing.medium)
            ) {
                Button(
                    onClick = onDismiss,
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
                    shape = RoundedCornerShape(components.buttonCornerRadius)
                ) {
                    Text("Cancel", color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
                Button(
                    onClick = onSave,
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(containerColor = HabitFlowColors.Primary),
                    shape = RoundedCornerShape(components.buttonCornerRadius)
                ) {
                    Text("Save Task", color = Color.White)
                }
            }
        }
    }
}

@Composable
private fun CategoryChip(
    category: TaskCategory,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val spacing = LocalHabitFlowSpacing.current
    Surface(
        modifier = modifier.then(if (selected) Modifier.border(2.dp, category.color, RoundedCornerShape(8.dp)) else Modifier),
        onClick = onClick,
        shape = RoundedCornerShape(8.dp),
        color = category.color.copy(alpha = 0.2f)
    ) {
        Text(
            text = category.label,
            style = MaterialTheme.typography.labelMedium,
            color = category.color,
            modifier = Modifier.padding(horizontal = spacing.medium, vertical = spacing.small)
        )
    }
}

@Composable
private fun PriorityChip(
    priority: TaskPriority,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val spacing = LocalHabitFlowSpacing.current
    Surface(
        modifier = modifier,
        onClick = onClick,
        shape = RoundedCornerShape(8.dp),
        color = priority.color.copy(alpha = 0.2f)
    ) {
        Row(
            modifier = Modifier.padding(horizontal = spacing.medium, vertical = spacing.small),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(spacing.tiny)
        ) {
            Box(
                modifier = Modifier
                    .size(8.dp)
                    .clip(CircleShape)
                    .background(priority.color)
            )
            Text(
                text = priority.label,
                style = MaterialTheme.typography.labelMedium,
                color = priority.color
            )
        }
    }
}