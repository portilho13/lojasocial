package com.example.mobile.data.remote.dto

import com.google.gson.annotations.SerializedName
import java.time.LocalDateTime

data class InventoryDto(
    @SerializedName("id")
    val id: String,

    @SerializedName("quantity")
    val quantity: Int,

    @SerializedName("expiryDate")
    val expiryDate: LocalDateTime,

    @SerializedName("location")
    val location: String,

    @SerializedName("productId")
    val productId: String,

    @SerializedName("productName")
    val productName: String
)