package com.hk.habitflow.task.util

actual object PlatformClock {
    actual fun currentTimeMillis(): Long = System.currentTimeMillis()
}
