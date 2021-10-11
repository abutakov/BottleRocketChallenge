package com.example.alltheweatherapp.model

import com.google.gson.annotations.Expose

data class Weather(
    @Expose
    val id: Int,
    @Expose
    val days: List<Day>
)
