package com.example.mobile.domain.models

import com.google.gson.annotations.SerializedName

data class CreateProductTypeRequest(
    @SerializedName("description")
    val description: String
)
