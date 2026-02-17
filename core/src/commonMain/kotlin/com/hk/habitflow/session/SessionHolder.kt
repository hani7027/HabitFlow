package com.hk.habitflow.session

/**
 * Holds the current logged-in user id after successful login.
 * Used by Tasks and Habits to scope data. Not persisted; cleared on app restart.
 */
object SessionHolder {
    var userId: String? = null
}
