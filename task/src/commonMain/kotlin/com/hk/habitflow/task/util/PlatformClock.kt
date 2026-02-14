package com.hk.habitflow.task.util

expect object PlatformClock {
    fun currentTimeMillis(): Long
}
