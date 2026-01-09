package com.example.mobile.data.repository

import com.example.mobile.common.Resource
import com.example.mobile.data.local.TokenManager
import com.example.mobile.data.remote.AuthApiService
import com.example.mobile.domain.models.LoginRequest
import com.example.mobile.domain.models.LoginResponse
import com.example.mobile.domain.repository.AuthRepository
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val apiService: AuthApiService,
    private val tokenManager: TokenManager
) : AuthRepository {

    override suspend fun login(request: LoginRequest, rememberMe: Boolean): Resource<LoginResponse> {
        return try {
            val response = apiService.login(request)

            if (response.isSuccessful && response.body() != null) {
                val loginResponse = response.body()!!

                // Guardar tokens
                tokenManager.saveAccessToken(loginResponse.accessToken)
                tokenManager.saveRefreshToken(loginResponse.refreshToken)

                // Guardar user info
                tokenManager.saveUserInfo(loginResponse.user)

                // Guardar preferência de remember me
                if (rememberMe) {
                    tokenManager.setRememberMe(true)
                }

                Resource.Success(loginResponse)
            } else {
                Resource.Error(
                    message = when (response.code()) {
                        401 -> "Email ou senha incorretos"
                        404 -> "Utilizador não encontrado"
                        else -> "Erro ao fazer login: ${response.message()}"
                    }
                )
            }
        } catch (e: HttpException) {
            Resource.Error(
                message = when (e.code()) {
                    401 -> "Email ou senha incorretos"
                    403 -> "Acesso negado"
                    404 -> "Utilizador não encontrado"
                    500 -> "Erro no servidor. Tente novamente mais tarde"
                    else -> "Erro de conexão: ${e.message()}"
                }
            )
        } catch (e: IOException) {
            Resource.Error(message = "Sem conexão à internet. Verifique a sua rede")
        } catch (e: Exception) {
            Resource.Error(message = "Erro inesperado: ${e.localizedMessage}")
        }
    }

    override suspend fun logout(): Resource<Unit> {
        return try {
            tokenManager.clearTokens()
            Resource.Success(Unit)
        } catch (e: Exception) {
            Resource.Error(message = "Erro ao fazer logout")
        }
    }

    override suspend fun refreshToken(): Resource<String> {
        return try {
            val refreshToken = tokenManager.getRefreshToken()
            if (refreshToken.isNullOrEmpty()) {
                return Resource.Error("Sessão expirada. Faça login novamente")
            }

            val response = apiService.refreshToken(mapOf("refreshToken" to refreshToken))

            if (response.isSuccessful && response.body() != null) {
                val newAccessToken = response.body()!!.accessToken
                tokenManager.saveAccessToken(newAccessToken)
                Resource.Success(newAccessToken)
            } else {
                tokenManager.clearTokens()
                Resource.Error("Sessão expirada. Faça login novamente")
            }
        } catch (e: Exception) {
            Resource.Error("Erro ao renovar sessão")
        }
    }

    override suspend fun isUserLoggedIn(): Boolean {
        val accessToken = tokenManager.getAccessToken()
        return !accessToken.isNullOrEmpty()
    }

    override suspend fun getCurrentUser(): Resource<com.example.mobile.domain.models.User> {
        return try {
            val user = tokenManager.getUserInfo()
            if (user != null) {
                Resource.Success(user)
            } else {
                Resource.Error("Utilizador não encontrado")
            }
        } catch (e: Exception) {
            Resource.Error("Erro ao obter dados do utilizador")
        }
    }
}