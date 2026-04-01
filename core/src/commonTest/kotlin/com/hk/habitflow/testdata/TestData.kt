package com.hk.habitflow.testdata

import com.hk.habitflow.domain.model.HabitFrequencyType
import com.hk.habitflow.domain.model.HabitIcon
import com.hk.habitflow.domain.model.HabitWithDetails
import com.hk.habitflow.domain.model.TaskCategory
import com.hk.habitflow.domain.model.TaskPriority
import com.hk.habitflow.domain.model.TaskWithDetails
import com.hk.habitflow.domain.model.User

/**
 * Shared test fixtures for all domain models.
 * Import this object in any commonTest to get pre-built, ready-to-use instances.
 */
object TestData {

    // ── User ─────────────────────────────────────────────────────────────────

    val user1 = User(
        id = "user-001",
        name = "Alice Smith",
        email = "alice@example.com",
        passwordHash = "hashed_password_alice",
        createdAt = 1_700_000_000_000L
    )

    val user2 = User(
        id = "user-002",
        name = "Bob Jones",
        email = "bob@example.com",
        passwordHash = "hashed_password_bob",
        createdAt = 1_700_100_000_000L
    )

    val users = listOf(user1, user2)

    // ── HabitIcon ─────────────────────────────────────────────────────────────

    val habitIconFitness = HabitIcon(id = "icon-001", name = "fitness")
    val habitIconBook    = HabitIcon(id = "icon-002", name = "book")
    val habitIconMeditate = HabitIcon(id = "icon-003", name = "meditate")

    val habitIcons = listOf(habitIconFitness, habitIconBook, habitIconMeditate)

    // ── HabitFrequencyType ────────────────────────────────────────────────────

    val frequencyDaily = HabitFrequencyType(
        id = "freq-001",
        name = "Daily",
        description = "Once every day"
    )

    val frequencyWeekly = HabitFrequencyType(
        id = "freq-002",
        name = "Weekly",
        description = "Once every week"
    )

    val frequencyMonthly = HabitFrequencyType(
        id = "freq-003",
        name = "Monthly",
        description = null
    )

    val habitFrequencyTypes = listOf(frequencyDaily, frequencyWeekly, frequencyMonthly)

    // ── HabitWithDetails ──────────────────────────────────────────────────────

    val habit1 = HabitWithDetails(
        id = "habit-001",
        userId = user1.id,
        name = "Morning Run",
        iconId = habitIconFitness.id,
        iconName = habitIconFitness.name,
        frequencyTypeId = frequencyDaily.id,
        frequencyTypeName = frequencyDaily.name,
        frequencyTypeDescription = frequencyDaily.description,
        targetValue = 30L,
        reminderTime = 1_700_000_007_200_000L,
        createdAt = 1_700_000_000_000L,
        isArchived = false
    )

    val habit2 = HabitWithDetails(
        id = "habit-002",
        userId = user1.id,
        name = "Read 20 Pages",
        iconId = habitIconBook.id,
        iconName = habitIconBook.name,
        frequencyTypeId = frequencyWeekly.id,
        frequencyTypeName = frequencyWeekly.name,
        frequencyTypeDescription = frequencyWeekly.description,
        targetValue = 20L,
        reminderTime = null,
        createdAt = 1_700_000_100_000L,
        isArchived = false
    )

    val habitArchived = HabitWithDetails(
        id = "habit-003",
        userId = user1.id,
        name = "Evening Meditation",
        iconId = habitIconMeditate.id,
        iconName = habitIconMeditate.name,
        frequencyTypeId = frequencyDaily.id,
        frequencyTypeName = frequencyDaily.name,
        frequencyTypeDescription = frequencyDaily.description,
        targetValue = 15L,
        reminderTime = null,
        createdAt = 1_699_000_000_000L,
        isArchived = true
    )

    val habits = listOf(habit1, habit2, habitArchived)

    // ── TaskCategory ──────────────────────────────────────────────────────────

    val categoryWork     = TaskCategory(id = "cat-001", name = "Work",     icon = "💼")
    val categoryPersonal = TaskCategory(id = "cat-002", name = "Personal", icon = "🏠")
    val categoryHealth   = TaskCategory(id = "cat-003", name = "Health",   icon = null)

    val taskCategories = listOf(categoryWork, categoryPersonal, categoryHealth)

    // ── TaskPriority ──────────────────────────────────────────────────────────

    val priorityHigh   = TaskPriority(id = "pri-001", name = "High",   color = "#FF3B30")
    val priorityMedium = TaskPriority(id = "pri-002", name = "Medium", color = "#FF9500")
    val priorityLow    = TaskPriority(id = "pri-003", name = "Low",    color = null)

    val taskPriorities = listOf(priorityHigh, priorityMedium, priorityLow)

    // ── TaskWithDetails ───────────────────────────────────────────────────────

    val task1 = TaskWithDetails(
        id = "task-001",
        userId = user1.id,
        title = "Finish quarterly report",
        description = "Include Q1 metrics and projections",
        categoryId = categoryWork.id,
        categoryName = categoryWork.name,
        categoryIcon = categoryWork.icon,
        priorityId = priorityHigh.id,
        priorityName = priorityHigh.name,
        priorityColor = priorityHigh.color,
        dueDate = 1_700_500_000_000L,
        reminderTime = 1_700_490_000_000L,
        isCompleted = false,
        createdAt = 1_700_000_000_000L,
        completedAt = null
    )

    val task2 = TaskWithDetails(
        id = "task-002",
        userId = user1.id,
        title = "Buy groceries",
        description = null,
        categoryId = categoryPersonal.id,
        categoryName = categoryPersonal.name,
        categoryIcon = categoryPersonal.icon,
        priorityId = priorityLow.id,
        priorityName = priorityLow.name,
        priorityColor = priorityLow.color,
        dueDate = null,
        reminderTime = null,
        isCompleted = false,
        createdAt = 1_700_100_000_000L,
        completedAt = null
    )

    val taskCompleted = TaskWithDetails(
        id = "task-003",
        userId = user1.id,
        title = "Schedule dentist appointment",
        description = "Annual check-up",
        categoryId = categoryHealth.id,
        categoryName = categoryHealth.name,
        categoryIcon = categoryHealth.icon,
        priorityId = priorityMedium.id,
        priorityName = priorityMedium.name,
        priorityColor = priorityMedium.color,
        dueDate = 1_700_200_000_000L,
        reminderTime = null,
        isCompleted = true,
        createdAt = 1_699_900_000_000L,
        completedAt = 1_700_200_500_000L
    )

    val tasks = listOf(task1, task2, taskCompleted)
}

