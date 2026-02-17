package com.hk.habitflow.database

import kotlin.coroutines.CoroutineContext

/**
 * Coroutine context for database operations (blocking IO).
 * Use this instead of Dispatchers.Default/IO in commonMain for KMP.
 */
expect val databaseDispatcher: CoroutineContext
