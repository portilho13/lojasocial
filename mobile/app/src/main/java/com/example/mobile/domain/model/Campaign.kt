package com.example.mobile.domain.model

import java.util.Date

data class Campaign(
    val id: String,
    val title: String,
    val description: String?,
    val startDate: String, // String for simplicity in JSON parsing, or Date if we use a serializer
    val endDate: String?,
    val isActive: Boolean,
    val donationCount: Int
)
