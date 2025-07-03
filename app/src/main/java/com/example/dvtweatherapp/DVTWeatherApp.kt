package com.example.dvtweatherapp

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import com.example.dvtweatherapp.di.appModule

class DVTWeatherApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@DVTWeatherApp)
            modules(appModule)
        }
    }
}