package com.example.mobile

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class SasApp : Application() {
    override fun onCreate() {
        super.onCreate()
    }
}