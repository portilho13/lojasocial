package com.example.mobile.domain.models

import java.util.Date

data class SupportRequest(
    val id: String,
    val date: String,
    val status: RequestStatus,
    val observation: String?,
    val studentId: String,
    val items: List<RequestItem> = emptyList()
)
