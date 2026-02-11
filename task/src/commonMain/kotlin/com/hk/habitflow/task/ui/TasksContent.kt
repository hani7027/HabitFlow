package com.hk.habitflow.task.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.hk.habitflow.task.TasksEvent
import com.hk.habitflow.task.TasksState
import com.hk.habitflow.task.TasksTab
import com.hk.habitflow.ui.theme.HabitFlowColors
import com.hk.habitflow.ui.theme.LocalHabitFlowSpacing

@Composable
fun TasksContent(
    state: TasksState,
    onEvent: (TasksEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    val spacing = LocalHabitFlowSpacing.current

    Column(modifier = modifier.fillMaxSize()) {
        TasksHeader(
            dateText = state.dateText,
            onSearchClick = { onEvent(TasksEvent.SearchClick) }
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = spacing.screenHorizontal, vertical = spacing.small),
            horizontalArrangement = Arrangement.spacedBy(spacing.small)
        ) {
            TasksTab.entries.forEach { tab ->
                val selected = state.selectedTab == tab
                Surface(
                    modifier = Modifier
                        .weight(1f)
                        .clip(MaterialTheme.shapes.medium)
                        .clickable { onEvent(TasksEvent.SelectTab(tab)) },
                    color = if (selected) MaterialTheme.colorScheme.surface else MaterialTheme.colorScheme.surfaceVariant,
                    shape = MaterialTheme.shapes.medium
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(vertical = spacing.small),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = tab.name.replaceFirstChar { it.uppercase() },
                            style = MaterialTheme.typography.labelLarge,
                            color = if (selected) HabitFlowColors.Primary else MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        }
        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = spacing.screenHorizontal)
                .padding(top = spacing.medium)
        ) {
            TasksCompletedCard(
                completedCount = state.completedCount,
                totalCount = state.totalCount,
                modifier = Modifier.padding(bottom = spacing.medium)
            )
            state.tasks.forEach { task ->
                TaskListCard(
                    task = task,
                    onCheckedChange = { onEvent(TasksEvent.TaskChecked(task.id, it)) },
                    onOptionsClick = { onEvent(TasksEvent.TaskOptions(task.id)) },
                    modifier = Modifier.padding(bottom = spacing.small)
                )
            }
        }
        FloatingActionButton(
            onClick = { onEvent(TasksEvent.AddTaskClick) },
            modifier = Modifier
                .align(Alignment.End)
                .padding(end = spacing.medium, bottom = 80.dp),
            containerColor = HabitFlowColors.Primary,
            contentColor = MaterialTheme.colorScheme.onPrimary,
            shape = MaterialTheme.shapes.medium
        ) {
            Text(text = "+", style = MaterialTheme.typography.headlineSmall)
        }
    }
}
