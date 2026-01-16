package com.example.mobile.data.remote.dto

import com.example.mobile.domain.models.Campaign

data class CampaignDto(
    val id: String,
    val title: String,
    val description: String?,
    val startDate: String,
    val endDate: String?,
    val isActive: Boolean,
    val donationCount: Int?
) {
    fun toDomain(): Campaign {
        return Campaign(
            id = id,
            title = title,
            description = description,
            startDate = startDate,
            endDate = endDate,
            isActive = isActive,
            donationCount = donationCount ?: 0
        )
    }
}
