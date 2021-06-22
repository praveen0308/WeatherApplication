package com.example.weatherapplication.models

data class ApiResponse(
    val current: Current,
    val forecast: Forecast,
    val location: Location
)