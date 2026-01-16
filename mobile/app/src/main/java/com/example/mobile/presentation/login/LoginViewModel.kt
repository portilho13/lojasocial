package com.example.mobile.presentation.login

import com.example.mobile.common.Resource
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobile.domain.models.LoginRequest
import com.example.mobile.domain.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class LoginState(
    val email: String = "",
    val password: String = "",
    val rememberMe: Boolean = false,
    val isPasswordVisible: Boolean = false,
    val isLoading: Boolean = false,
    val error: String? = null,
    val isLoginSuccessful: Boolean = false,
    val emailError: String? = null,
    val passwordError: String? = null
)

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _state = MutableStateFlow(LoginState())
    val state: StateFlow<LoginState> = _state.asStateFlow()

    fun onEvent(event: LoginEvent) {
        when (event) {
            is LoginEvent.EmailChanged -> {
                _state.update { it.copy(
                    email = event.email,
                    emailError = null,
                    error = null
                ) }
            }
            is LoginEvent.PasswordChanged -> {
                _state.update { it.copy(
                    password = event.password,
                    passwordError = null,
                    error = null
                ) }
            }
            is LoginEvent.RememberMeChanged -> {
                _state.update { it.copy(rememberMe = event.rememberMe) }
            }
            is LoginEvent.TogglePasswordVisibility -> {
                _state.update { it.copy(isPasswordVisible = !it.isPasswordVisible) }
            }
            is LoginEvent.Login -> {
                login()
            }
            is LoginEvent.ClearError -> {
                _state.update { it.copy(error = null) }
            }
        }
    }

    private fun login() {
        val currentState = _state.value

        // Validação local
        if (!validateInput()) return
        if (currentState.isLoading) return

        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null) }

            val loginRequest = LoginRequest(
                email = currentState.email.trim(),
                password = currentState.password
            )

            when (val result = authRepository.login(loginRequest, currentState.rememberMe)) {
                is Resource.Success<*> -> {
                    _state.update { it.copy(
                        isLoading = false,
                        isLoginSuccessful = true,
                        error = null
                    ) }
                }
                is Resource.Error<*> -> {
                    _state.update { it.copy(
                        isLoading = false,
                        error = result.message ?: "Erro ao fazer login",
                        isLoginSuccessful = false
                    ) }
                }
                is Resource.Loading<*> -> {
                    _state.update { it.copy(isLoading = true) }
                }
            }
        }
    }

    private fun validateInput(): Boolean {
        val currentState = _state.value
        var isValid = true

        // Validação de email
        if (currentState.email.isBlank()) {
            _state.update { it.copy(emailError = "Email é obrigatório") }
            isValid = false
        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(currentState.email).matches()) {
            _state.update { it.copy(emailError = "Email inválido") }
            isValid = false
        } else if (!currentState.email.endsWith("@ipca.pt") &&
            !currentState.email.endsWith("@alunos.ipca.pt")) {
            _state.update { it.copy(emailError = "Utilize um email IPCA válido") }
            isValid = false
        }

        // Validação de password
        if (currentState.password.isBlank()) {
            _state.update { it.copy(passwordError = "Senha é obrigatória") }
            isValid = false
        } else if (currentState.password.length < 6) {
            _state.update { it.copy(passwordError = "Senha deve ter no mínimo 6 caracteres") }
            isValid = false
        }

        return isValid
    }

    fun resetLoginSuccess() {
        _state.update { it.copy(isLoginSuccessful = false) }
    }
}

sealed class LoginEvent {
    data class EmailChanged(val email: String) : LoginEvent()
    data class PasswordChanged(val password: String) : LoginEvent()
    data class RememberMeChanged(val rememberMe: Boolean) : LoginEvent()
    object TogglePasswordVisibility : LoginEvent()
    object Login : LoginEvent()
    object ClearError : LoginEvent()
}