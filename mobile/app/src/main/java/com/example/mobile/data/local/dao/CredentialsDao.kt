package com.example.mobile.data.local.dao

import androidx.room.*
import com.example.mobile.data.local.entity.SavedCredentials

@Dao
interface CredentialsDao {

    @Query("SELECT * FROM saved_credentials WHERE id = 1")
    suspend fun getSavedCredentials(): SavedCredentials?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveCredentials(credentials: SavedCredentials)

    @Query("DELETE FROM saved_credentials")
    suspend fun clearCredentials()

    @Query("SELECT rememberMe FROM saved_credentials WHERE id = 1")
    suspend fun isRememberMeEnabled(): Boolean?
}