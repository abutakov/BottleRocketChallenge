package com.example.alltheweatherapp.di

import android.content.Context
import com.example.alltheweatherapp.model.WeatherRepositoryImpl

object DI {
    fun provideRepository() = WeatherRepositoryImpl()

    var context: Context? = null
}