package com.hk.habitflow.environment

enum class AppEnvironment {
    Dev,
    Qa,
    Uat
}

object AppEnvironmentHolder {
    var current: AppEnvironment = AppEnvironment.Dev
}

fun setAppEnvironment(raw: String?) {
    AppEnvironmentHolder.current = when (raw?.trim()?.lowercase()) {
        "dev" -> AppEnvironment.Dev
        "qa" -> AppEnvironment.Qa
        "uat" -> AppEnvironment.Uat
        else -> AppEnvironment.Dev
    }
}
