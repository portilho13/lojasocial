package com.example.mobile.di

import com.example.mobile.data.remote.SupportRequestApiService
import com.example.mobile.data.repository.SupportRequestRepositoryImpl
import com.example.mobile.domain.repository.SupportRequestRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RequestModule {

    @Provides
    @Singleton
    fun provideSupportRequestApiService(retrofit: Retrofit): SupportRequestApiService {
        return retrofit.create(SupportRequestApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideSupportRequestRepository(
        apiService: SupportRequestApiService
    ): SupportRequestRepository {
        return SupportRequestRepositoryImpl(apiService)
    }
}
