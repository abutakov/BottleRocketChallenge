package com.example.alltheweatherapp.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.alltheweatherapp.R
import com.example.alltheweatherapp.databinding.HourlyWeatherLayoutBinding
import com.example.alltheweatherapp.databinding.WeeklyWeatherHeaderLayoutBinding
import com.example.alltheweatherapp.model.Day
import com.example.alltheweatherapp.model.HourlyWeather

import com.squareup.picasso.Picasso

class WeatherScrollAdapter(private val dataSet: Day):
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    class WeatherScrollViewHolder(private val binding: HourlyWeatherLayoutBinding):
        RecyclerView.ViewHolder(binding.root){
        fun onBind(weather: HourlyWeather){
            var hour = weather.hour.toString()
            if (weather.hour >= 12)
                hour = (weather.hour%12).toString() + "PM"
            else
                hour += "AM"

            binding.hourlyHumidity.text = weather.humidity.toString()
            binding.hourlyRain.text = weather.rainChance.toString()
            binding.hourlyTemp.text = weather.temperature.toString()
            binding.hourlyTime.text = hour
            binding.hourlyWind.text = weather.windSpeed.toString()
            when(weather.weatherType){
                "sunny"->
                    Picasso.get().load(R.drawable.ic_icon_weather_active_ic_sunny_active)
                        .into(binding.hourlyIcon)
                "cloudy"->
                    Picasso.get().load(R.drawable.ic_icon_weather_active_ic_partly_cloudy_active)
                        .into(binding.hourlyIcon)
                "heavyRain"->
                    Picasso.get().load(R.drawable.ic_icon_weather_active_ic_heavy_rain_active)
                        .into(binding.hourlyIcon)
                "lightRain"->
                    Picasso.get().load(R.drawable.ic_icon_weather_active_ic_light_rain_active)
                        .into(binding.hourlyIcon)
                "partlyCloudy"->
                    Picasso.get().load(R.drawable.ic_icon_weather_active_ic_partly_cloudy_active)
                        .into(binding.hourlyIcon)
                "snowSleet"->
                    Picasso.get().load(R.drawable.ic_icon_weather_active_ic_snow_sleet_active)
                        .into(binding.hourlyIcon)
            }
        }
    }
    class WeatherHeaderViewHolder(binding: WeeklyWeatherHeaderLayoutBinding): RecyclerView.ViewHolder(binding.root){
    }

    override fun getItemViewType(position: Int): Int {
        return if(position == 0) 0 else position
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            when(viewType) {
                0 -> WeatherHeaderViewHolder(WeeklyWeatherHeaderLayoutBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                ))
                else -> WeatherScrollViewHolder(HourlyWeatherLayoutBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                ))
            }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder) {
            is WeatherScrollViewHolder ->
                holder.onBind(dataSet.hourlyWeatherList[position])
        }
    }

    override fun getItemCount() = dataSet.hourlyWeatherList.size
}