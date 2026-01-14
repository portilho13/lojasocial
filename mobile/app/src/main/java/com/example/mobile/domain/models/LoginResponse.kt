package com.example.mobile.domain.models

import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("accessToken")
    val accessToken: String,

    @SerializedName("refreshToken")
    val refreshToken: String,

    @SerializedName("user")
    val user: User? = null  // Tornar opcional
)