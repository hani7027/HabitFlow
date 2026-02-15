package com.hk.habitflow.task.util

import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.atStartOfDayIn
import kotlinx.datetime.plus
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime

fun hourMinuteFromEpoch(epochMs: Long): Pair<Int, Int> {
    val instant = kotlinx.datetime.Instant.fromEpochMilliseconds(epochMs)
    val local = instant.toLocalDateTime(TimeZone.currentSystemDefault())
    return local.hour to local.minute
}

fun startOfDayEpoch(epochMs: Long): Long {
    val instant = kotlinx.datetime.Instant.fromEpochMilliseconds(epochMs)
    val local = instant.toLocalDateTime(TimeZone.currentSystemDefault())
    return local.date.atStartOfDayIn(TimeZone.currentSystemDefault()).toEpochMilliseconds()
}

fun epochFromDayAndTime(dayStartEpochMs: Long, hour: Int, minute: Int): Long {
    val instant = kotlinx.datetime.Instant.fromEpochMilliseconds(dayStartEpochMs)
    val local = instant.toLocalDateTime(TimeZone.currentSystemDefault())
    val time = LocalTime(hour, minute, 0, 0)
    val withTime = LocalDateTime(local.date, time)
    return withTime.toInstant(TimeZone.currentSystemDefault()).toEpochMilliseconds()
}

fun startOfDayEpochForDaysFromNow(daysFromNow: Int): Long {
    val zone = TimeZone.currentSystemDefault()
    val nowMs = PlatformClock.currentTimeMillis()
    val today = kotlinx.datetime.Instant.fromEpochMilliseconds(nowMs).toLocalDateTime(zone).date
    val target = today.plus(daysFromNow, DateTimeUnit.DAY)
    return target.atStartOfDayIn(zone).toEpochMilliseconds()
}
