package com.example.mobile.domain.repository

import com.example.mobile.common.Resource
import com.example.mobile.domain.model.RequestStatus
import com.example.mobile.domain.model.SupportRequest

interface SupportRequestRepository {
    suspend fun getRequests(): Resource<List<SupportRequest>>
    suspend fun updateStatus(id: String, status: RequestStatus): Resource<SupportRequest>
}
