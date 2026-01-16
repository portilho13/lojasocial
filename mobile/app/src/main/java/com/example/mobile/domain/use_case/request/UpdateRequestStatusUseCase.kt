package com.example.mobile.domain.use_case.request

import com.example.mobile.common.Resource
import com.example.mobile.domain.models.RequestStatus
import com.example.mobile.domain.models.SupportRequest
import com.example.mobile.domain.repository.SupportRequestRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class UpdateRequestStatusUseCase @Inject constructor(
    private val repository: SupportRequestRepository
) {
    operator fun invoke(id: String, status: RequestStatus): Flow<Resource<SupportRequest>> = flow {
        emit(Resource.Loading())
        val result = repository.updateStatus(id, status)
        emit(result)
    }
}
