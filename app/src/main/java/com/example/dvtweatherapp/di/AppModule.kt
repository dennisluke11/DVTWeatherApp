package com.example.dvtweatherapp.di

import com.example.dvtweatherapp.data.IWeatherRepository
import com.example.dvtweatherapp.data.WeatherApi
import com.example.dvtweatherapp.data.WeatherRepository
import com.example.dvtweatherapp.ui.weather.componets.WeatherViewModel
import com.example.dvtweatherapp.utils.LocationHelper
import okhttp3.OkHttpClient
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val appModule = module {

    single {
        Retrofit.Builder()
            .baseUrl("https://api.openweathermap.org/data/2.5/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(OkHttpClient.Builder().build())
            .build()
    }

    single<WeatherApi> {
        get<Retrofit>().create(WeatherApi::class.java)
    }

    single<IWeatherRepository> { WeatherRepository(get()) }

    single { LocationHelper(get()) }

    viewModel { WeatherViewModel(get(), get()) }
}