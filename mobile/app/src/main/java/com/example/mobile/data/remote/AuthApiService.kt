package com.example.mobile.data.remote

import com.example.mobile.domain.models.LoginRequest
import com.example.mobile.domain.models.LoginResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApiService {

    @POST("auth/login")
    suspend fun login(
        @Body request: LoginRequest
    ): Response<LoginResponse>

    @POST("auth/refresh")
    suspend fun refreshToken(
        @Body request: Map<String, String>
    ): Response<LoginResponse>

    @POST("auth/logout")
    suspend fun logout(): Response<Unit>
}
