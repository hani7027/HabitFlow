package com.hk.habitflow.habit.util

actual object PlatformClock {
    actual fun currentTimeMillis(): Long = System.currentTimeMillis()
}
