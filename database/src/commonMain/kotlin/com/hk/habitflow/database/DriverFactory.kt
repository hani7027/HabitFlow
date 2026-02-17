package com.hk.habitflow.database

import app.cash.sqldelight.db.SqlDriver

/**
 * Platform-specific factory for SQLDelight driver.
 * On Android pass Application context; on iOS pass null.
 */
expect class DriverFactory(platformContext: Any?) {
    fun createDriver(): SqlDriver
}
