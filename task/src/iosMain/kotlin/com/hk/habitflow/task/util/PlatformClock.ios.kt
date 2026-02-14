package com.hk.habitflow.task.util

import kotlinx.cinterop.ExperimentalForeignApi
import platform.posix.time

@OptIn(ExperimentalForeignApi::class)
actual object PlatformClock {
    actual fun currentTimeMillis(): Long = (time(null)?.toLong() ?: 0L) * 1000L
}
