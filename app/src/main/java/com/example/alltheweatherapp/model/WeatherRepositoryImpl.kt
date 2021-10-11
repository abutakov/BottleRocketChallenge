package com.example.alltheweatherapp.model

import android.content.Context
import android.util.Log
import com.example.alltheweatherapp.api.BottleRocketService
import com.example.alltheweatherapp.api.RetrofitClient
import com.example.alltheweatherapp.di.DI

private const val TAG = "WeatherRepositoryImpl"
class WeatherRepositoryImpl: WeatherRepository,
    WeatherRepository.CitySearchRepository {
    private val STORED_CITIES = "WeatherRepositoryImpl_STORED_CITIES"
    private val CITY_SHARED_PREFS_TAG = "WeatherRepositoryImpl_CITY_SHARED_PREFS_TAG"
    private val api = RetrofitClient.getService(BottleRocketService::class.java)

    override suspend fun getCityDetails(geoId: Int): CityDetailsState{
        val data = api.getCityDetails(geoId)
        return if (data.isSuccessful)
            CityDetailsState.Response(data.body()!!)
        else
            CityDetailsState.Error(data.message())
    }

    override suspend fun getCityList(input: String): CityState {
        val data = api.getCityList(input)
        return if (data.isSuccessful) {
            if (data.body() != null) CityState.Response(data.body()!!)
            else CityState.Error(data.message())
        }
        else CityState.Error(data.message())
    }

    override fun storeCityDetails(geoNameId: Int) {
        Log.d(TAG, "storeCity: $geoNameId")
        val citiesSet:MutableSet<String> =
            DI.context?.getSharedPreferences(CITY_SHARED_PREFS_TAG,
                Context.MODE_PRIVATE)?.getStringSet(STORED_CITIES, null) ?: mutableSetOf()

        citiesSet.add(geoNameId.toString())
        DI.context?.getSharedPreferences(CITY_SHARED_PREFS_TAG,
            Context.MODE_PRIVATE)?.edit()
            ?.putStringSet(STORED_CITIES, citiesSet)
            ?.apply()
    }

    sealed class CityDetailsState{
        data class Response(val cityDetails: CityDetails): CityDetailsState()
        object Loading: CityDetailsState()
        data class Error(val errorMessage: String): CityDetailsState()
    }

    sealed class CityState{
        data class Response(val cityList: CitySearchResponse): CityState()
        object Loading: CityState()
        data class Error(val errorMessage: String): CityState()
    }
}