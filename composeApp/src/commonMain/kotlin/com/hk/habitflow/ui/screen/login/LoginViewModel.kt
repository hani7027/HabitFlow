package com.hk.habitflow.ui.screen.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hk.habitflow.domain.usecase.LoginUseCase
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LoginViewModel(
    private val loginUseCase: LoginUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(LoginContract.State())
    val state: StateFlow<LoginContract.State> = _state.asStateFlow()

    private val _effect = Channel<LoginContract.Effect>()
    val effect = _effect.receiveAsFlow()

    fun onEvent(event: LoginContract.Event) {
        when (event) {
            is LoginContract.Event.EmailChanged -> {
                _state.update { 
                    it.copy(
                        email = event.value,
                        emailError = null,
                        error = null
                    )
                }
            }
            is LoginContract.Event.PasswordChanged -> {
                _state.update { 
                    it.copy(
                        password = event.value,
                        passwordError = null,
                        error = null
                    )
                }
            }
            LoginContract.Event.LoginClicked -> {
                login()
            }
        }
    }

    private fun login() = viewModelScope.launch {
        val currentState = _state.value
        
        if (currentState.email.isBlank()) {
            _state.update { it.copy(emailError = "Email cannot be empty") }
            return@launch
        }
        
        if (currentState.password.isBlank()) {
            _state.update { it.copy(passwordError = "Password cannot be empty") }
            return@launch
        }

        _state.update { it.copy(isLoading = true, error = null) }

        loginUseCase(currentState.email, currentState.password)
            .onSuccess {
                _state.update { it.copy(isLoading = false) }
                _effect.send(LoginContract.Effect.NavigateToHome)
            }
            .onFailure { exception ->
                _state.update { 
                    it.copy(
                        isLoading = false,
                        error = exception.message ?: "Login failed"
                    )
                }
                _effect.send(LoginContract.Effect.ShowToast(exception.message ?: "Login failed"))
            }
    }
}
