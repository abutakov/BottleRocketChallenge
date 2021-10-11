package com.example.alltheweatherapp.view

import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.Glide.with
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition

import com.example.alltheweatherapp.R
import com.example.alltheweatherapp.databinding.ActivityMainBinding
import com.example.alltheweatherapp.databinding.HomeToolbarBinding
import com.example.alltheweatherapp.di.DI
import com.example.alltheweatherapp.model.CityDetails
import com.example.alltheweatherapp.model.Day
import com.example.alltheweatherapp.model.WeatherRepositoryImpl
import com.example.alltheweatherapp.viewmodel.WeatherViewModel
import com.squareup.picasso.Picasso
import java.text.SimpleDateFormat

import java.util.*

class MainActivity : AppCompatActivity(), SearchCityFragment.SelectedCity {
    private lateinit var binding: ActivityMainBinding
    private lateinit var includeBinding: HomeToolbarBinding
    private val viewmodel: WeatherViewModel by lazy{
        WeatherViewModel.WeatherViewModelFactory(DI.provideRepository()).create(
                WeatherViewModel::class.java
        )
    }
    private lateinit var scrollAdapter: WeatherScrollAdapter
    private lateinit var weeklyAdapter: WeatherWeeklyAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        includeBinding = binding.mainToolbar
        setContentView(binding.root)
        initViews()

        viewmodel.cityDetailsState.observe(this){
            when(it){
                is WeatherRepositoryImpl.CityDetailsState.Response -> {
                    updateData(it.cityDetails)
                    showLoading(true)
                }
                is WeatherRepositoryImpl.CityDetailsState.Error -> {
                    showError(it.errorMessage)
                    showLoading(true)
                }
                is WeatherRepositoryImpl.CityDetailsState.Loading -> showLoading(false)
            }
        }
        checkPreviousCities()
    }

    private fun checkPreviousCities() {
        val cities = getSharedPreferences("WeatherRepositoryImpl_CITY_SP", MODE_PRIVATE)
                .getStringSet("WeatherRepositoryImpl_SAVED_CITIES", emptySet())

        viewmodel.getCityDetails(
                cities?.firstOrNull()?.toInt() ?: 1
        )
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.weather_menu,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            android.R.id.home->{
                openSearchFragment()
                true
            }
            R.id.delete_city->{
                deleteCurrentCity()
                true
            }
            else-> false
        }
    }

    private fun openSearchFragment() {
        supportFragmentManager.beginTransaction()
                .replace(android.R.id.content, SearchCityFragment())
                .addToBackStack(null)
                .commit()
    }

    private fun deleteCurrentCity() {
        Toast.makeText(this, "Current city deleted", Toast.LENGTH_SHORT).show()
    }

    private fun initViews(){
        binding.weeklyForecast.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false)
        binding.forecast.layoutManager = LinearLayoutManager(this)
//        setSupportActionBar(binding.mainToolbar.toolbar)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_icon_search)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)
    }

    private fun showLoading(hasData: Boolean) {
        if(hasData) binding.progressBar.visibility = View.GONE
        else binding.progressBar.visibility = View.VISIBLE
    }

    private fun showError(errorMessage: String) {
        Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show()
    }

    private fun updateData(data: CityDetails) {
        weeklyAdapter = WeatherWeeklyAdapter(data){
            createForecastAdapter(it)
        }
        binding.mainToolbar.hometoolbarcity.textCity.text = data.city.name
        binding.mainToolbar.hometoolbarcity.textDate.text = SimpleDateFormat("E MM/dd/yy hh:mm a", Locale.getDefault()).format(Date())
        binding.mainToolbar.hometoolbarcity.textDegrees.text = "${data.weather.days[0].low.toString()}º"
        binding.weeklyForecast.adapter = weeklyAdapter
        Picasso
            .get().load(data.city.imageURLs.androidImageURL?.xhdpiImageURL)
            .placeholder(R.drawable.img_dallas)
            .into(binding.mainToolbar.currentCity)
    }

    private fun createForecastAdapter(data: Day) {
        scrollAdapter = WeatherScrollAdapter(data)
        binding.forecast.adapter = scrollAdapter
    }

    override fun selectedCity(geoId: Int) {
        viewmodel.getCityDetails(geoId)
    }
}