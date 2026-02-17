package com.hk.habitflow.database

import android.content.Context
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver

actual class DriverFactory actual constructor(private val platformContext: Any?) {
    actual fun createDriver(): SqlDriver {
        val ctx = platformContext as? Context
            ?: throw IllegalStateException("Android DriverFactory requires Application Context")
        return AndroidSqliteDriver(
            schema = HabitFlowDatabase.Schema,
            context = ctx.applicationContext,
            name = "habitflow.db"
        )
    }
}
