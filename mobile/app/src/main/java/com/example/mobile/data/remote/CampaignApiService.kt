package com.example.mobile.data.remote

import com.example.mobile.data.remote.dto.CampaignDto
import com.example.mobile.data.remote.dto.CreateCampaignDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface CampaignApiService {

    @GET("api/v1/campaigns")
    suspend fun getCampaigns(): Response<List<CampaignDto>>

    @GET("api/v1/campaigns/active")
    suspend fun getActiveCampaigns(): Response<List<CampaignDto>>

    @POST("api/v1/campaigns")
    suspend fun createCampaign(@Body campaign: CreateCampaignDto): Response<CampaignDto>

    @DELETE("api/v1/campaigns/{id}")
    suspend fun deleteCampaign(@Path("id") id: String): Response<Unit>
}
