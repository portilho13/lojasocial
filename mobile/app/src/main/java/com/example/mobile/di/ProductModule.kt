package com.example.mobile.di

import com.example.mobile.data.remote.ProductApiService
import com.example.mobile.data.repository.ProductRepositoryImpl
import com.example.mobile.domain.manager.CategoryManager
import com.example.mobile.domain.repository.ProductRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ProductModule {

    @Provides
    @Singleton
    fun provideProductApiService(retrofit: Retrofit): ProductApiService {
        return retrofit.create(ProductApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideProductRepository(
        apiService: ProductApiService
    ): ProductRepository {
        return ProductRepositoryImpl(apiService)
    }

    @Provides
    @Singleton
    fun provideCategoryManager(
        repository: ProductRepository
    ): CategoryManager {
        return CategoryManager(repository)
    }
}