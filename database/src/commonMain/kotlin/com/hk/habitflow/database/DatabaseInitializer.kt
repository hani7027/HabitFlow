package com.hk.habitflow.database

import com.hk.habitflow.database.HabitFlowDatabase
import kotlinx.coroutines.withContext

/**
 * Initializes the database and seeds reference data if empty.
 * Call once after creating the driver (e.g. at app startup) from a coroutine.
 */
suspend fun initializeDatabase(database: HabitFlowDatabase) = withContext(databaseDispatcher) {
    val categories = database.taskCategoryQueries.selectAll().executeAsList()
    if (categories.isEmpty()) {
        database.seedDataQueries.seedTaskCategories()
        database.seedDataQueries.seedTaskPriorities()
        database.seedDataQueries.seedHabitIcons()
        database.seedDataQueries.seedHabitFrequencyTypes()
    }
}
