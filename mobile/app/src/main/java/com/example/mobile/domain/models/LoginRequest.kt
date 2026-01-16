package com.example.mobile.domain.models

import com.google.gson.annotations.SerializedName

data class LoginRequest(
    @SerializedName("email")
    val email: String,

    @SerializedName("password")
    val password: String
)

data class LoginStudentRequest(
    @SerializedName("email")
    val email: String,

    @SerializedName("password")
    val password: String
)