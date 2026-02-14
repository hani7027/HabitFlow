package com.hk.habitflow.task.util

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

actual object TimeFormatter {
    private val timeFormat = SimpleDateFormat("h:mm a", Locale.getDefault())
    private val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())

    actual fun formatTime(epochMs: Long?): String {
        if (epochMs == null) return "--:--"
        return timeFormat.format(Date(epochMs))
    }

    actual fun formatDate(epochMs: Long?): String {
        if (epochMs == null) return ""
        return dateFormat.format(Date(epochMs))
    }
}
