package com.hk.habitflow.domain.repository

import com.hk.habitflow.domain.model.HabitFrequencyType
import com.hk.habitflow.domain.model.HabitIcon
import com.hk.habitflow.domain.model.HabitWithDetails
import kotlinx.coroutines.flow.Flow

/**
 * User-scoped habit operations. All queries filter by userId.
 */
interface HabitRepository {
    fun getHabitIcons(): Flow<List<HabitIcon>>
    fun getHabitFrequencyTypes(): Flow<List<HabitFrequencyType>>
    fun observeHabitsByUserId(userId: String): Flow<List<HabitWithDetails>>
    suspend fun getHabitById(userId: String, habitId: String): HabitWithDetails?
    suspend fun insertHabit(habit: HabitWithDetails)
    suspend fun updateHabit(habit: HabitWithDetails)
    suspend fun deleteHabit(userId: String, habitId: String)
}
