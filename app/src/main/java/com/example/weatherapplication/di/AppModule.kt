package com.example.weatherapplication.di

import com.example.weatherapplication.api.WeatherApi
import com.example.weatherapplication.repositories.WeatherRepo
import com.example.weatherapplication.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(ApplicationComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit = Retrofit.Builder()
        .baseUrl(Constants.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Singleton
    @Provides
    fun provideWeatherApi(retrofit: Retrofit): WeatherApi =
        retrofit.create(WeatherApi::class.java)


    @Singleton
    @Provides
    fun provideWeatherRepo(weatherApi: WeatherApi): WeatherRepo =
        WeatherRepo(weatherApi)

}