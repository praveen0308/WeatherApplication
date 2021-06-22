package com.example.weatherapplication.viewmodels

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.weatherapplication.models.ApiResponse
import com.example.weatherapplication.repositories.WeatherRepo
import java.util.*
import kotlin.properties.ObservableProperty

class WeatherViewModel @ViewModelInject constructor(
    private val weatherRepo: WeatherRepo
): ViewModel() {

    suspend fun getCurrentWeather(location:String):ApiResponse = weatherRepo.getCurrentWeather(location)

    suspend fun getForecastWeather(location: String):ApiResponse = weatherRepo.getForecastWeather(location)
}