package com.example.alltheweatherapp.model

import com.google.gson.annotations.Expose

data class CitySearchResponse(
    @Expose
    val totalCitiesFound: Int,
    @Expose
    val startIndex: Int,
    @Expose
    val cities: List<City>
)