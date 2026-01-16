package com.example.mobile.data.remote

import com.example.mobile.data.remote.dto.SupportRequestDto
import com.example.mobile.data.remote.dto.UpdateStatusDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.Path

interface SupportRequestApiService {

    @GET("api/v1/support-requests")
    suspend fun getRequests(): Response<List<SupportRequestDto>>

    @PATCH("api/v1/support-requests/{id}/status")
    suspend fun updateStatus(
        @Path("id") id: String,
        @Body status: UpdateStatusDto
    ): Response<SupportRequestDto>
}
