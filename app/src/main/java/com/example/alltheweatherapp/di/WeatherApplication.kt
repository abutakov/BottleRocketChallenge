package com.example.alltheweatherapp.di

import android.app.Application

class WeatherApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        DI.context = applicationContext
    }
}