package com.hk.habitflow.platform

/**
 * Platform context for database driver. Set before any screen that needs DB (e.g. in MainActivity onCreate).
 * Android: Application context; iOS: null.
 */
expect object PlatformContext {
    var value: Any?
}
