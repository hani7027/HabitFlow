package com.hk.habitflow

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform