package com.example.mobile.domain.use_case.request

import com.example.mobile.common.Resource
import com.example.mobile.domain.model.SupportRequest
import com.example.mobile.domain.repository.SupportRequestRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetRequestsUseCase @Inject constructor(
    private val repository: SupportRequestRepository
) {
    operator fun invoke(): Flow<Resource<List<SupportRequest>>> = flow {
        emit(Resource.Loading())
        val result = repository.getRequests()
        emit(result)
    }
}
