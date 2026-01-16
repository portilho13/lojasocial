package com.example.mobile.domain.model

data class RequestItem(
    val id: String,
    val productId: String,
    val productName: String,
    val qtyRequested: Int,
    val qtyDelivered: Int,
    val observation: String?
)
