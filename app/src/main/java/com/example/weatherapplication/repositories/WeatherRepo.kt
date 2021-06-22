package com.example.weatherapplication.repositories

import com.example.weatherapplication.api.WeatherApi
import com.example.weatherapplication.models.ApiResponse
import com.example.weatherapplication.utils.Constants
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WeatherRepo @Inject constructor(private val weatherApi: WeatherApi) {

    suspend fun getCurrentWeather(query:String) : ApiResponse = weatherApi.getCurrentWeather(Constants.API_KEY,query,"no")

    suspend fun getForecastWeather(query: String) : ApiResponse = weatherApi.getForecastWeather(Constants.API_KEY,query,5,"no","no")
}