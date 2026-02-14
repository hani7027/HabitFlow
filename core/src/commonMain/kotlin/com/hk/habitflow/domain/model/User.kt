package com.hk.habitflow.domain.model

data class User(
    val id: String,
    val name: String,
    val email: String,
    val passwordHash: String,
    val createdAt: Long
)
