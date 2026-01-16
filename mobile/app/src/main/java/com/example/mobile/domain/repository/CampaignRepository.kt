package com.example.mobile.domain.repository

import com.example.mobile.common.Resource
import com.example.mobile.data.remote.dto.CreateCampaignDto
import com.example.mobile.domain.model.Campaign

interface CampaignRepository {
    suspend fun getCampaigns(): Resource<List<Campaign>>
    suspend fun createCampaign(campaign: CreateCampaignDto): Resource<Campaign>
    suspend fun deleteCampaign(id: String): Resource<Unit>
}
