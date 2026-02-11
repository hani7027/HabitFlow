package com.hk.habitflow.task.model

import androidx.compose.ui.graphics.Color
import com.hk.habitflow.ui.theme.HabitFlowColors

enum class TaskCategory(val label: String, val color: Color) {
    Work("Work", HabitFlowColors.CategoryWork),
    Personal("Personal", HabitFlowColors.CategoryPersonal),
    Health("Health", HabitFlowColors.CategoryHealth),
    Shopping("Shopping", HabitFlowColors.CategoryShopping),
    Learning("Learning", HabitFlowColors.CategoryLearning),
    Other("Other", HabitFlowColors.CategoryOther)
}
