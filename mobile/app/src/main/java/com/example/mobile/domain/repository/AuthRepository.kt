package com.example.mobile.domain.repository

import com.example.mobile.domain.models.LoginRequest
import com.example.mobile.domain.models.LoginResponse
import com.example.mobile.common.Resource

interface AuthRepository {
    suspend fun login(request: LoginRequest, rememberMe: Boolean): Resource<LoginResponse>
    suspend fun logout(): Resource<Unit>
    suspend fun refreshToken(): Resource<String>
    suspend fun isUserLoggedIn(): Boolean
    suspend fun getCurrentUser(): Resource<com.example.mobile.domain.models.User>
}
