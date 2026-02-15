package com.hk.habitflow.database

import com.hk.habitflow.database.HabitFlowDatabase
import com.hk.habitflow.domain.model.HabitFrequencyType
import com.hk.habitflow.domain.model.HabitIcon
import com.hk.habitflow.domain.model.HabitWithDetails
import com.hk.habitflow.domain.repository.HabitRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class HabitRepositoryImpl(
    private val database: HabitFlowDatabase
) : HabitRepository {

    private val queries = database.habitQueries
    private val iconQueries = database.habitIconQueries
    private val frequencyQueries = database.habitFrequencyTypeQueries

    override fun getHabitIcons(): Flow<List<HabitIcon>> = flow {
        emit(iconQueries.selectAll().executeAsList().map { it.toHabitIcon() })
    }.flowOn(databaseDispatcher)

    override fun getHabitFrequencyTypes(): Flow<List<HabitFrequencyType>> = flow {
        emit(frequencyQueries.selectAll().executeAsList().map { it.toFrequencyType() })
    }.flowOn(databaseDispatcher)

    override fun observeHabitsByUserId(userId: String): Flow<List<HabitWithDetails>> = flow {
        emit(queries.selectHabitsAllByUserId(userId).executeAsList().map { it.toHabitWithDetails() })
    }.flowOn(databaseDispatcher)

    override suspend fun getHabitById(userId: String, habitId: String): HabitWithDetails? = withContext(databaseDispatcher) {
        queries.selectHabitByUserIdAndId(userId, habitId).executeAsOneOrNull()?.toHabitWithDetails()
    }

    override suspend fun insertHabit(habit: HabitWithDetails) = withContext(databaseDispatcher) {
        queries.insert(
            id = habit.id,
            userId = habit.userId,
            name = habit.name,
            iconId = habit.iconId,
            frequencyTypeId = habit.frequencyTypeId,
            targetValue = habit.targetValue,
            reminderTime = habit.reminderTime,
            createdAt = habit.createdAt,
            isArchived = if (habit.isArchived) 1L else 0L
        )
    }

    override suspend fun updateHabit(habit: HabitWithDetails) = withContext(databaseDispatcher) {
        queries.update(
            name = habit.name,
            iconId = habit.iconId,
            frequencyTypeId = habit.frequencyTypeId,
            targetValue = habit.targetValue,
            reminderTime = habit.reminderTime,
            isArchived = if (habit.isArchived) 1L else 0L,
            id = habit.id,
            userId = habit.userId
        )
    }

    override suspend fun deleteHabit(userId: String, habitId: String) = withContext(databaseDispatcher) {
        queries.deleteById(habitId, userId)
    }
}

private fun com.hk.habitflow.database.HabitIcon.toHabitIcon(): HabitIcon = com.hk.habitflow.domain.model.HabitIcon(
    id = id,
    name = name
)

private fun com.hk.habitflow.database.HabitFrequencyType.toFrequencyType(): HabitFrequencyType = HabitFrequencyType(
    id = id,
    name = name,
    description = description
)

private fun com.hk.habitflow.database.SelectHabitsAllByUserId.toHabitWithDetails(): HabitWithDetails = habitWithDetailsFromRow(id, userId, name, iconId, iconName, frequencyTypeId, frequencyTypeName, frequencyTypeDescription, targetValue, reminderTime, createdAt, isArchived)

private fun com.hk.habitflow.database.SelectHabitByUserIdAndId.toHabitWithDetails(): HabitWithDetails = habitWithDetailsFromRow(id, userId, name, iconId, iconName, frequencyTypeId, frequencyTypeName, frequencyTypeDescription, targetValue, reminderTime, createdAt, isArchived)

private fun habitWithDetailsFromRow(
    id: String,
    userId: String,
    name: String,
    iconId: String,
    iconName: String,
    frequencyTypeId: String,
    frequencyTypeName: String,
    frequencyTypeDescription: String?,
    targetValue: Long,
    reminderTime: Long?,
    createdAt: Long,
    isArchived: Long
): HabitWithDetails = HabitWithDetails(
    id = id,
    userId = userId,
    name = name,
    iconId = iconId,
    iconName = iconName,
    frequencyTypeId = frequencyTypeId,
    frequencyTypeName = frequencyTypeName,
    frequencyTypeDescription = frequencyTypeDescription,
    targetValue = targetValue,
    reminderTime = reminderTime,
    createdAt = createdAt,
    isArchived = isArchived != 0L
)
