package com.example.alltheweatherapp.api

import com.example.alltheweatherapp.model.City
import com.example.alltheweatherapp.model.CityDetails
import com.example.alltheweatherapp.model.CitySearchResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface BottleRocketService {
    @GET("cities")
    suspend fun getCityList(
        @Query("search") city: String
    ): Response<CitySearchResponse>

    @GET("cities/{cityId}")
    suspend fun getCityDetails(
        @Path("cityId") cityId: Int
    ): Response<CityDetails>
}