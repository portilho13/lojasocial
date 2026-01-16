package com.example.mobile.domain.use_case.campaign

import com.example.mobile.common.Resource
import com.example.mobile.data.remote.dto.CreateCampaignDto
import com.example.mobile.domain.models.Campaign
import com.example.mobile.domain.repository.CampaignRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CreateCampaignUseCase @Inject constructor(
    private val repository: CampaignRepository
) {
    operator fun invoke(campaign: CreateCampaignDto): Flow<Resource<Campaign>> = flow {
        emit(Resource.Loading())
        val result = repository.createCampaign(campaign)
        emit(result)
    }
}
