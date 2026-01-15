package com.example.mobile.domain.models

import com.google.gson.annotations.SerializedName

data class CreateProductTypeRequest(
    @SerializedName("name")
    val name: String,

    @SerializedName("description")
    val description: String,

    @SerializedName("productTypeId")
    val productTypeId: String
)