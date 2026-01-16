package com.example.mobile.presentation.campaigns

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobile.common.Resource
import com.example.mobile.data.remote.dto.CreateCampaignDto
import com.example.mobile.domain.models.Campaign
import com.example.mobile.domain.use_case.campaign.CreateCampaignUseCase
import com.example.mobile.domain.use_case.campaign.DeleteCampaignUseCase
import com.example.mobile.domain.use_case.campaign.GetCampaignsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

data class CampaignsState(
    val campaigns: List<Campaign> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
    val isCampaignCreated: Boolean = false // Signal for navigation or dialog close
)

@HiltViewModel
class CampaignViewModel @Inject constructor(
    private val getCampaignsUseCase: GetCampaignsUseCase,
    private val createCampaignUseCase: CreateCampaignUseCase,
    private val deleteCampaignUseCase: DeleteCampaignUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(CampaignsState())
    val state: StateFlow<CampaignsState> = _state.asStateFlow()

    init {
        getCampaigns()
    }

    fun getCampaigns() {
        getCampaignsUseCase().onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _state.value = _state.value.copy(
                        campaigns = result.data ?: emptyList(),
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

    fun createCampaign(title: String, description: String?, startDate: String, endDate: String?) {
        val campaignRequest = CreateCampaignDto(
            title = title,
            description = description,
            startDate = startDate,
            endDate = endDate
        )

        createCampaignUseCase(campaignRequest).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _state.value = _state.value.copy(
                        isLoading = false,
                        isCampaignCreated = true,
                        error = null
                    )
                    getCampaigns() // Refresh list
                }
                is Resource.Error -> {
                    _state.value = _state.value.copy(
                        isLoading = false,
                        error = result.message ?: "Failed to create campaign"
                    )
                }
                is Resource.Loading -> {
                    _state.value = _state.value.copy(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }

     fun deleteCampaign(id: String) {
        deleteCampaignUseCase(id).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _state.value = _state.value.copy(isLoading = false)
                    getCampaigns() // Refresh list
                }
                is Resource.Error -> {
                    _state.value = _state.value.copy(
                        error = result.message ?: "Failed to delete campaign",
                        isLoading = false
                    )
                }
                is Resource.Loading -> {
                    _state.value = _state.value.copy(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }

    fun resetCampaignCreatedState() {
        _state.value = _state.value.copy(isCampaignCreated = false)
    }
    
    fun clearError() {
        _state.value = _state.value.copy(error = null)
    }
}
