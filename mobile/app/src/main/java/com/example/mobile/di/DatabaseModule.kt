package com.example.mobile.di

import android.content.Context
import com.example.mobile.data.local.AppDatabase
import com.example.mobile.data.local.CredentialsManager
import com.example.mobile.data.local.dao.CredentialsDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(
        @ApplicationContext context: Context
    ): AppDatabase {
        return AppDatabase.getDatabase(context)
    }

    @Provides
    @Singleton
    fun provideCredentialsDao(database: AppDatabase): CredentialsDao {
        return database.credentialsDao()
    }

    @Provides
    @Singleton
    fun provideCredentialsManager(
        credentialsDao: CredentialsDao
    ): CredentialsManager {
        return CredentialsManager(credentialsDao)
    }
}
