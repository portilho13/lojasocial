package com.example.mobile.di

import com.example.mobile.data.remote.CampaignApiService
import com.example.mobile.data.repository.CampaignRepositoryImpl
import com.example.mobile.domain.repository.CampaignRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CampaignModule {

    @Provides
    @Singleton
    fun provideCampaignApiService(retrofit: Retrofit): CampaignApiService {
        return retrofit.create(CampaignApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideCampaignRepository(
        apiService: CampaignApiService
    ): CampaignRepository {
        return CampaignRepositoryImpl(apiService)
    }
}
