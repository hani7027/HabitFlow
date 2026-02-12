package com.hk.habitflow.habit.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.hk.habitflow.habit.HabitsEvent
import com.hk.habitflow.habit.HabitsState
import com.hk.habitflow.ui.theme.HabitFlowColors
import com.hk.habitflow.ui.theme.LocalHabitFlowSpacing

@Composable
fun HabitsContent(
    state: HabitsState,
    onEvent: (HabitsEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    val spacing = LocalHabitFlowSpacing.current

    Column(modifier = modifier.fillMaxSize()) {
        HabitsHeader(onCalendarClick = { onEvent(HabitsEvent.CalendarClick) })
        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = spacing.screenHorizontal)
                .padding(top = spacing.medium)
        ) {
            TodayProgressCard(
                completedCount = state.completedCount,
                remainingCount = state.remainingCount,
                modifier = Modifier.padding(bottom = spacing.medium)
            )
            state.habits.forEach { habit ->
                HabitListCard(
                    habit = habit,
                    onCompleteClick = {
                        if (habit.isCountable) onEvent(HabitsEvent.HabitProgressIncrement(habit.id))
                        else onEvent(HabitsEvent.HabitCompleteToggled(habit.id))
                    },
                    modifier = Modifier.padding(bottom = spacing.small)
                )
            }
            ThisWeekCard(
                weekDayStatuses = state.weekDayStatuses,
                modifier = Modifier.padding(top = spacing.small, bottom = spacing.extraLarge)
            )
        }
        FloatingActionButton(
            onClick = { },
            modifier = Modifier
                .align(Alignment.End)
                .padding(end = spacing.medium, bottom = 80.dp),
            containerColor = HabitFlowColors.Success,
            contentColor = MaterialTheme.colorScheme.onPrimary,
            shape = MaterialTheme.shapes.medium
        ) {
            Text(text = "+", style = MaterialTheme.typography.headlineSmall)
        }
    }
}
