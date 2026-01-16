package com.example.mobile.presentation.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobile.common.Resource
import com.example.mobile.domain.models.RegisterRequest
import com.example.mobile.domain.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class RegisterState(
    val name: String = "",
    val userType: String = "",
    val contact: String = "",
    val email: String = "",
    val password: String = "",
    val isPasswordVisible: Boolean = false,
    val isLoading: Boolean = false,
    val error: String? = null,
    val isRegisterSuccessful: Boolean = false,
    val nameError: String? = null,
    val userTypeError: String? = null,
    val contactError: String? = null,
    val emailError: String? = null,
    val passwordError: String? = null
)

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _state = MutableStateFlow(RegisterState())
    val state: StateFlow<RegisterState> = _state.asStateFlow()

    // Mapeamento de tipos de utilizador UI -> Backend
    private val userTypeMap = mapOf(
        "Estudante" to "VOLUNTARIO",
        "Docente" to "STAFF",
        "Técnico (Admin)" to "ADMIN"
    )

    fun onEvent(event: RegisterEvent) {
        when (event) {
            is RegisterEvent.NameChanged -> {
                _state.update { it.copy(
                    name = event.name,
                    nameError = null,
                    error = null
                ) }
            }
            is RegisterEvent.UserTypeChanged -> {
                _state.update { it.copy(
                    userType = event.userType,
                    userTypeError = null,
                    error = null
                ) }
            }
            is RegisterEvent.ContactChanged -> {
                _state.update { it.copy(
                    contact = event.contact,
                    contactError = null,
                    error = null
                ) }
            }
            is RegisterEvent.EmailChanged -> {
                _state.update { it.copy(
                    email = event.email,
                    emailError = null,
                    error = null
                ) }
            }
            is RegisterEvent.PasswordChanged -> {
                _state.update { it.copy(
                    password = event.password,
                    passwordError = null,
                    error = null
                ) }
            }
            is RegisterEvent.TogglePasswordVisibility -> {
                _state.update { it.copy(isPasswordVisible = !it.isPasswordVisible) }
            }
            is RegisterEvent.Register -> {
                register()
            }
            is RegisterEvent.ClearError -> {
                _state.update { it.copy(error = null) }
            }
        }
    }

    private fun register() {
        if (!validateInput()) return

        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null) }

            val currentState = _state.value
            val backendUserType = userTypeMap[currentState.userType] ?: "STUDENT"

            val registerRequest = RegisterRequest(
                name = currentState.name.trim(),
                userType = backendUserType,
                contact = currentState.contact.trim(),
                email = currentState.email.trim(),
                password = currentState.password
            )

            when (val result = authRepository.register(registerRequest)) {
                is Resource.Success<*> -> {
                    _state.update { it.copy(
                        isLoading = false,
                        isRegisterSuccessful = true,
                        error = null
                    ) }
                }
                is Resource.Error<*> -> {
                    _state.update { it.copy(
                        isLoading = false,
                        error = result.message ?: "Erro ao criar conta",
                        isRegisterSuccessful = false
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

        // Validação de nome
        if (currentState.name.isBlank()) {
            _state.update { it.copy(nameError = "Nome é obrigatório") }
            isValid = false
        } else if (currentState.name.length < 3) {
            _state.update { it.copy(nameError = "Nome deve ter pelo menos 3 caracteres") }
            isValid = false
        }

        // Validação de tipo de utilizador
        if (currentState.userType.isBlank()) {
            _state.update { it.copy(userTypeError = "Selecione o tipo de utilizador") }
            isValid = false
        }

        // Validação de contacto
        if (currentState.contact.isBlank()) {
            _state.update { it.copy(contactError = "Contacto é obrigatório") }
            isValid = false
        } else if (!currentState.contact.matches(Regex("^[0-9]{9}$"))) {
            _state.update { it.copy(contactError = "Contacto deve ter 9 dígitos") }
            isValid = false
        }

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

    fun resetRegisterSuccess() {
        _state.update { it.copy(isRegisterSuccessful = false) }
    }
}

sealed class RegisterEvent {
    data class NameChanged(val name: String) : RegisterEvent()
    data class UserTypeChanged(val userType: String) : RegisterEvent()
    data class ContactChanged(val contact: String) : RegisterEvent()
    data class EmailChanged(val email: String) : RegisterEvent()
    data class PasswordChanged(val password: String) : RegisterEvent()
    object TogglePasswordVisibility : RegisterEvent()
    object Register : RegisterEvent()
    object ClearError : RegisterEvent()
}