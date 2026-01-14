package com.example.mobile.domain.models

import com.google.gson.annotations.SerializedName

data class RegisterRequest(
    @SerializedName("name")
    val name: String,

    @SerializedName("userType")
    val userType: String,

    @SerializedName("contact")
    val contact: String,

    @SerializedName("email")
    val email: String,

    @SerializedName("password")
    val password: String
)