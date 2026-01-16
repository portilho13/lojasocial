package com.example.mobile.di

import android.content.Context
import com.example.mobile.data.local.CredentialsManager
import com.example.mobile.data.local.TokenManager
import com.example.mobile.data.remote.AuthApiService
import com.example.mobile.data.repository.AuthRepositoryImpl
import com.example.mobile.domain.repository.AuthRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideTokenManager(
        @ApplicationContext context: Context
    ): TokenManager {
        return TokenManager(context)
    }

    @Provides
    @Singleton
    fun provideAuthRepository(
        apiService: AuthApiService,
        tokenManager: TokenManager,
        credentialsManager: CredentialsManager
    ): AuthRepository {
        return AuthRepositoryImpl(apiService, tokenManager, credentialsManager)
    }
}
