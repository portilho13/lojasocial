package com.example.mobile.data.local

import com.example.mobile.data.local.dao.CredentialsDao
import com.example.mobile.data.local.entity.SavedCredentials
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CredentialsManager @Inject constructor(
    private val credentialsDao: CredentialsDao
) {

    suspend fun getSavedCredentials() = credentialsDao.getSavedCredentials()

    suspend fun saveCredentials(credentials: SavedCredentials) {
        credentialsDao.saveCredentials(credentials)
    }

    suspend fun clearCredentials() {
        credentialsDao.clearCredentials()
    }
}