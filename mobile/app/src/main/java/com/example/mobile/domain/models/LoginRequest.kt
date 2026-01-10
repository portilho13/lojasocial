package com.example.mobile.domain.models

import com.google.gson.annotations.SerializedName

data class LoginRequest(
    @SerializedName("email")
    val email: String,

    @SerializedName("password")
    val password: String
)

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

data class RegisterResponse(
    @SerializedName("id")
    val id: String,

    @SerializedName("name")
    val name: String,

    @SerializedName("userType")
    val userType: String,

    @SerializedName("contact")
    val contact: String,

    @SerializedName("email")
    val email: String
)

data class LoginResponse(
    @SerializedName("accessToken")
    val accessToken: String,

    @SerializedName("refreshToken")
    val refreshToken: String,

    @SerializedName("user")
    val user: User? = null  // Tornar opcional
)

data class User(
    @SerializedName("id")
    val id: String,

    @SerializedName("email")
    val email: String,

    @SerializedName("name")
    val name: String,

    @SerializedName("role")
    val role: String // STUDENT, ADMIN, etc.
)
