package com.example.mobile.presentation.requests.admin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobile.common.Resource
import com.example.mobile.domain.models.RequestStatus
import com.example.mobile.domain.models.SupportRequest
import com.example.mobile.domain.use_case.request.GetRequestsUseCase
import com.example.mobile.domain.use_case.request.UpdateRequestStatusUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

data class RequestsState(
    val requests: List<SupportRequest> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)

@HiltViewModel
class RequestsViewModel @Inject constructor(
    private val getRequestsUseCase: GetRequestsUseCase,
    private val updateRequestStatusUseCase: UpdateRequestStatusUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(RequestsState())
    val state: StateFlow<RequestsState> = _state.asStateFlow()

    init {
        getRequests()
    }

    fun getRequests() {
        getRequestsUseCase().onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _state.value = _state.value.copy(
                        requests = result.data ?: emptyList(),
                        isLoading = false,
                        error = null
                    )
                }
                is Resource.Error -> {
                    _state.value = _state.value.copy(
                        error = result.message ?: "An unexpected error occurred",
                        isLoading = false
                    )
                }
                is Resource.Loading -> {
                    _state.value = _state.value.copy(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }

    fun updateStatus(id: String, status: RequestStatus) {
        updateRequestStatusUseCase(id, status).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    // Refresh list after update or manually update the item in list
                    getRequests() 
                }
                is Resource.Error -> {
                     _state.value = _state.value.copy(
                        error = result.message ?: "Failed to update status",
                        isLoading = false
                    )
                }
                is Resource.Loading -> {
                    _state.value = _state.value.copy(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }
}
