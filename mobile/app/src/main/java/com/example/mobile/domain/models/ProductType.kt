package com.example.mobile.domain.models

import com.google.gson.annotations.SerializedName


data class ProductType(
    @SerializedName("id")
    val id: String,

    @SerializedName("description")
    val description: String
)

data class CreateProductTypeRequest(
    @SerializedName("description")
    val description: String,
)