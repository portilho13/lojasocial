package com.example.mobile.domain.use_case.campaign

import com.example.mobile.common.Resource
import com.example.mobile.domain.model.Campaign
import com.example.mobile.domain.repository.CampaignRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetCampaignsUseCase @Inject constructor(
    private val repository: CampaignRepository
) {
    operator fun invoke(): Flow<Resource<List<Campaign>>> = flow {
        emit(Resource.Loading())
        val result = repository.getCampaigns()
        emit(result)
    }
}
