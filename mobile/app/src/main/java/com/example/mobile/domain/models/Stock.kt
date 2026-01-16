package com.example.mobile.domain.models

import com.google.gson.annotations.SerializedName
import java.time.LocalDateTime

data class CreateStockRequest(
    @SerializedName("productId")
    val id: String,

    @SerializedName("quantity")
    val quantity: Number,

    @SerializedName("expiryDate")
    val expiryDate: String,

    @SerializedName("location")
    val location: String,
)

data class StockDto(
    @SerializedName("id")
    val id: String,

    @SerializedName("quantity")
    val quantity: Int,

    @SerializedName("expiryDate")
    val expiryDate: String,

    @SerializedName("location")
    val location: String,

    @SerializedName("productId")
    val productId: String,

    @SerializedName("productName")
    val productName: String
)

data class UpdateStockRequest(
    @SerializedName("quantity")
    val quantity: Int? = null,

    @SerializedName("expiryDate")
    val expiryDate: String? = null,

    @SerializedName("location")
    val location: String? = null
)

data class StockSummaryDto(
    @SerializedName("totalItems")
    val totalItems: Int,

    @SerializedName("totalProducts")
    val totalProducts: Int,

    @SerializedName("expiringCount")
    val expiringCount: Int,

    @SerializedName("lowStockCount")
    val lowStockCount: Int? = null
)