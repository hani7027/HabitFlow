package com.hk.habitflow.ui.screen.home

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.hk.habitflow.ui.component.HabitCard
import com.hk.habitflow.ui.component.HomeHeader
import com.hk.habitflow.ui.component.ProgressRingCard
import com.hk.habitflow.ui.component.QuickFocusCard
import com.hk.habitflow.ui.component.SectionHeader
import com.hk.habitflow.ui.component.TaskItemRow
import com.hk.habitflow.ui.theme.LocalHabitFlowSpacing

@Composable
fun HomeContent(
    state: HomeState,
    onEvent: (HomeEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    val spacing = LocalHabitFlowSpacing.current
    val scrollState = rememberScrollState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
    ) {
        HomeHeader(
            greeting = state.greeting,
            dateText = state.dateText
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = spacing.screenHorizontal)
                .padding(top = spacing.medium)
        ) {
            ProgressRingCard(
                completedCount = state.completedCount,
                totalCount = state.totalCount,
                taskCount = state.taskCount,
                habitCount = state.habitCount,
                modifier = Modifier.padding(bottom = spacing.medium)
            )
            SectionHeader(
                title = "Today's Tasks",
                viewAllClick = { onEvent(HomeEvent.ViewAllTasks) }
            )
            state.tasks.forEach { task ->
                TaskItemRow(
                    task = task,
                    onCheckedChange = { onEvent(HomeEvent.TaskChecked(task.id, it)) },
                    modifier = Modifier.padding(bottom = spacing.small)
                )
            }
            Spacer(modifier = Modifier.height(spacing.medium))
            SectionHeader(
                title = "Today's Habits",
                viewAllClick = { onEvent(HomeEvent.ViewAllHabits) }
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState())
                    .padding(vertical = spacing.small),
                horizontalArrangement = Arrangement.spacedBy(spacing.small)
            ) {
                state.habits.forEach { habit ->
                    HabitCard(
                        habit = habit,
                        onClick = { onEvent(HomeEvent.HabitClicked(habit.id)) }
                    )
                }
            }
            Spacer(modifier = Modifier.height(spacing.medium))
            QuickFocusCard(
                focusMinutes = state.focusMinutes,
                onStartClick = { onEvent(HomeEvent.StartFocus) },
                modifier = Modifier.padding(bottom = spacing.extraLarge)
            )
        }
    }
}
