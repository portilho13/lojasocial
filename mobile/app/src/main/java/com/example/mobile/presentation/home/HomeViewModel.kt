package com.example.mobile.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobile.common.Resource
import com.example.mobile.domain.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class HomeState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val isLoggedOut: Boolean = false,
    val userName: String = "",
    val userEmail: String = "",
    val userType: String = ""
)

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _state = MutableStateFlow(HomeState())
    val state: StateFlow<HomeState> = _state.asStateFlow()

    init {
        loadUserInfo()
    }

    fun onEvent(event: HomeEvent) {
        when (event) {
            is HomeEvent.Logout -> {
                logout()
            }
            is HomeEvent.ClearError -> {
                _state.update { it.copy(error = null) }
            }
        }
    }

    private fun loadUserInfo() {
        viewModelScope.launch {
            when (val result = authRepository.getCurrentUser()) {
                is Resource.Success -> {
                    result.data?.let { user ->
                        _state.update { it.copy(
                            userName = user.name,
                            userEmail = user.email,
                            userType = user.role
                        ) }
                    }
                }
                is Resource.Error -> {
                    _state.update { it.copy(error = result.message) }
                }
                is Resource.Loading -> {
                    _state.update { it.copy(isLoading = true) }
                }
            }
        }
    }

    private fun logout() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }

            when (val result = authRepository.logout()) {
                is Resource.Success -> {
                    _state.update { it.copy(
                        isLoading = false,
                        isLoggedOut = true
                    ) }
                }
                is Resource.Error -> {
                    _state.update { it.copy(
                        isLoading = false,
                        error = result.message ?: "Erro ao fazer logout"
                    ) }
                }
                is Resource.Loading -> {
                    _state.update { it.copy(isLoading = true) }
                }
            }
        }
    }

    fun resetLogoutState() {
        _state.update { it.copy(isLoggedOut = false) }
    }
}

sealed class HomeEvent {
    object Logout : HomeEvent()
    object ClearError : HomeEvent()
}