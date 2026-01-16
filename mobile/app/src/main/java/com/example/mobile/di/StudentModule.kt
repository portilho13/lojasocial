package com.example.mobile.di

import com.example.mobile.data.remote.StudentApiService
import com.example.mobile.data.repository.StudentRepositoryImpl
import com.example.mobile.domain.repository.StudentRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object StudentModule {

    @Provides
    @Singleton
    fun provideStudentApiService(retrofit: Retrofit): StudentApiService {
        return retrofit.create(StudentApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideStudentRepository(
        apiService: StudentApiService
    ): StudentRepository {
        return StudentRepositoryImpl(apiService)
    }
}