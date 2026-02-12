package com.hk.habitflow.ui.screen.login

interface LoginContract {
    data class State(
        val isLoading: Boolean = false,
        val email: String = "test@example.com",
        val password: String = "password123",
        val emailError: String? = null,
        val passwordError: String? = null,
        val error: String? = null
    )

    sealed class Event {
        data class EmailChanged(val value: String) : Event()
        data class PasswordChanged(val value: String) : Event()
        object LoginClicked : Event()
    }

    sealed class Effect {
        object NavigateToHome : Effect()
        data class ShowToast(val message: String) : Effect()
    }
}
