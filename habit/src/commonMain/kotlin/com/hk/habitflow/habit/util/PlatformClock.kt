package com.hk.habitflow.habit.util

expect object PlatformClock {
    fun currentTimeMillis(): Long
}
