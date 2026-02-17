package com.hk.habitflow.database

import kotlin.coroutines.CoroutineContext
import kotlinx.coroutines.Dispatchers

actual val databaseDispatcher: CoroutineContext = Dispatchers.Default
