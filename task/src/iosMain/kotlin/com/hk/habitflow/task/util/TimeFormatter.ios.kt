package com.hk.habitflow.task.util

import platform.Foundation.NSDate
import platform.Foundation.NSDateFormatter
import platform.Foundation.NSTimeZone
import platform.Foundation.localTimeZone

actual object TimeFormatter {
    actual fun formatTime(epochMs: Long?): String {
        if (epochMs == null) return "--:--"
        val formatter = NSDateFormatter().apply {
            dateFormat = "h:mm a"
            timeZone = NSTimeZone.localTimeZone
        }
        val date = NSDate(epochMs / 1000.0)
        return formatter.stringFromDate(date) ?: "--:--"
    }

    actual fun formatDate(epochMs: Long?): String {
        if (epochMs == null) return ""
        val formatter = NSDateFormatter().apply {
            dateFormat = "dd/MM/yyyy"
            timeZone = NSTimeZone.localTimeZone
        }
        val date = NSDate(epochMs / 1000.0)
        return formatter.stringFromDate(date) ?: ""
    }
}
