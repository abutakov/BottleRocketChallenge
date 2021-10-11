package com.example.alltheweatherapp.viewmodel

import androidx.lifecycle.*
import com.example.alltheweatherapp.model.WeatherRepository
import com.example.alltheweatherapp.model.WeatherRepositoryImpl
import kotlinx.coroutines.launch

class WeatherSearchCity(private val repository: WeatherRepository.CitySearchRepository):
    ViewModel() {

    class WeatherSearchCityProvider(private val repository: WeatherRepository.CitySearchRepository):
            ViewModelProvider.Factory{
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return WeatherSearchCity(repository) as T
        }
    }

    private val cityMutableLiveData = MutableLiveData<WeatherRepositoryImpl.CityState>()

    val cityLiveData: LiveData<WeatherRepositoryImpl.CityState>
    get() {return cityMutableLiveData}

    fun searchCity(input: String){
        WeatherRepositoryImpl.CityState.Loading
        viewModelScope.launch {
            cityMutableLiveData.value = repository.getCityList(input)
        }
    }

    fun storeSelectedCity(geoNameId: Int){
        repository.storeCityDetails(geoNameId)
    }
}