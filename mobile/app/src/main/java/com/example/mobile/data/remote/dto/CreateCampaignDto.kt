package com.example.mobile.data.remote.dto

data class CreateCampaignDto(
    val title: String,
    val description: String?,
    val startDate: String, // ISO 8601 format
    val endDate: String?   // ISO 8601 format
)
