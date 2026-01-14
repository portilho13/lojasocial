package com.example.mobile.data.remote

import com.example.mobile.domain.models.LoginRequest
import com.example.mobile.domain.models.LoginResponse
import com.example.mobile.domain.models.RegisterRequest
import com.example.mobile.domain.models.RegisterResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApiService {

    @POST("/api/v1/auth/user/sign-in")
    suspend fun login(
        @Body request: LoginRequest
    ): Response<LoginResponse>

    @POST("/api/v1/auth/user/refresh")
    suspend fun refreshToken(
        @Body request: Map<String, String>
    ): Response<LoginResponse>

    @POST("/api/v1/auth/user/logout")
    suspend fun logout(
        @Body request: Map<String, String> // {"refreshToken": "..."}
    ): Response<Unit>

    @POST("/api/v1/auth/user/sign-up")
    suspend fun register(
        @Body request: RegisterRequest
    ): Response<RegisterResponse>
}
