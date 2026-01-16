package com.example.mobile.di

import com.example.mobile.data.remote.ProductApiService
import com.example.mobile.data.remote.StockApiService
import com.example.mobile.data.repository.ProductRepositoryImpl
import com.example.mobile.data.repository.StockRepositoryImpl
import com.example.mobile.domain.repository.ProductRepository
import com.example.mobile.domain.repository.StockRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object StockModule {

    @Provides
    @Singleton
    fun provideStockApiService(retrofit: Retrofit): StockApiService {
        return retrofit.create(StockApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideStockRepository(
        apiService: StockApiService
    ): StockRepository {
        return StockRepositoryImpl(apiService)
    }
}