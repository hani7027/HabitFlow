package com.hk.habitflow.task.util

/**
 * Formats epoch millis for display. Used when mapping domain task to UI.
 */
expect object TimeFormatter {
    fun formatTime(epochMs: Long?): String
    fun formatDate(epochMs: Long?): String
}
