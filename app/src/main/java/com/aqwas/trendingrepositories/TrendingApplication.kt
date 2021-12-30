package com.aqwas.trendingrepositories

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class TrendingApplication : Application() {

    override fun onCreate() {
        super.onCreate()

    }
}