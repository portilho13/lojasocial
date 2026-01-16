package com.example.mobile.data.remote.dto

import com.example.mobile.domain.model.RequestStatus
import com.example.mobile.domain.model.SupportRequest

data class SupportRequestDto(
    val id: String,
    val date: String,
    val status: String,
    val observation: String?,
    val studentId: String,
    val items: List<RequestItemDto> = emptyList()
) {
    fun toDomain(): SupportRequest {
        return SupportRequest(
            id = id,
            date = date,
            status = try {
                RequestStatus.valueOf(status)
            } catch (e: Exception) {
                RequestStatus.PENDENTE // Fallback
            },
            observation = observation,
            studentId = studentId,
            items = items.map { it.toDomain() }
        )
    }
}

data class RequestItemDto(
    val id: String,
    val productId: String,
    val productName: String,
    val qtyRequested: Int,
    val qtyDelivered: Int?,
    val observation: String?
) {
    fun toDomain(): com.example.mobile.domain.model.RequestItem {
        return com.example.mobile.domain.model.RequestItem(
            id = id,
            productId = productId,
            productName = productName,
            qtyRequested = qtyRequested,
            qtyDelivered = qtyDelivered ?: 0,
            observation = observation
        )
    }
}
