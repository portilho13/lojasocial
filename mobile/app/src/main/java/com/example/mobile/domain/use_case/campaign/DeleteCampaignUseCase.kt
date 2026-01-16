package com.example.mobile.domain.use_case.campaign

import com.example.mobile.common.Resource
import com.example.mobile.domain.repository.CampaignRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class DeleteCampaignUseCase @Inject constructor(
    private val repository: CampaignRepository
) {
    operator fun invoke(id: String): Flow<Resource<Unit>> = flow {
        emit(Resource.Loading())
        val result = repository.deleteCampaign(id)
        emit(result)
    }
}
