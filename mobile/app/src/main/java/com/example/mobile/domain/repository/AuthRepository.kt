package com.example.mobile.domain.repository

import com.example.mobile.domain.models.LoginRequest
import com.example.mobile.common.Resource
import com.example.mobile.data.local.entity.SavedCredentials
import com.example.mobile.data.remote.dto.LoginResponse
import com.example.mobile.data.remote.dto.RegisterResponse
import com.example.mobile.domain.models.RegisterRequest

interface AuthRepository {
    suspend fun login(request: LoginRequest, rememberMe: Boolean): Resource<LoginResponse>
    suspend fun register(request: RegisterRequest): Resource<RegisterResponse>
    suspend fun logout(): Resource<Unit>
    suspend fun refreshToken(): Resource<String>
    suspend fun isUserLoggedIn(): Boolean
    suspend fun getCurrentUser(): Resource<com.example.mobile.domain.models.User>
    suspend fun getSavedCredentials(): SavedCredentials? // Adicionar
}
