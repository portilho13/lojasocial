package com.example.mobile.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "saved_credentials")
data class SavedCredentials(
    @PrimaryKey(autoGenerate = false)
    val id: Int = 1,
    val email: String,
    val password: String,
    val rememberMe: Boolean,
    val lastLoginAt: Long = System.currentTimeMillis()
)