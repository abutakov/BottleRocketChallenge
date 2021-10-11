package com.example.alltheweatherapp.viewmodel

import androidx.lifecycle.*
import com.example.alltheweatherapp.model.WeatherRepository
import com.example.alltheweatherapp.model.WeatherRepositoryImpl
import kotlinx.coroutines.launch

class WeatherViewModel(private val repositoryImpl: WeatherRepository): ViewModel() {
    private val _cityDetailsState = MutableLiveData<WeatherRepositoryImpl.CityDetailsState>()

    val cityDetailsState: LiveData<WeatherRepositoryImpl.CityDetailsState>
        get() = this._cityDetailsState

    fun getCityDetails(geoId: Int){
        this._cityDetailsState.value = WeatherRepositoryImpl.CityDetailsState.Loading
        viewModelScope.launch {
            val data = repositoryImpl.getCityDetails(geoId)
            this@WeatherViewModel._cityDetailsState.value = data
        }
    }

    class WeatherViewModelFactory(private val repositoryImpl: WeatherRepository):
        ViewModelProvider.Factory{
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return WeatherViewModel(repositoryImpl) as T
        }
    }
}