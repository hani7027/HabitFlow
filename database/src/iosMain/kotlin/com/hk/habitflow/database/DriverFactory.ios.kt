package com.hk.habitflow.database

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.native.NativeSqliteDriver

actual class DriverFactory actual constructor(platformContext: Any?) {
    actual fun createDriver(): SqlDriver {
        return NativeSqliteDriver(
            schema = HabitFlowDatabase.Schema,
            name = "habitflow.db"
        )
    }
}
