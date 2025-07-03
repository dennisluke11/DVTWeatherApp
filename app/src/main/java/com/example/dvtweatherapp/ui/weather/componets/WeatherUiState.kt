package com.example.dvtweatherapp.ui.weather.componets

import com.example.dvtweatherapp.data.WeatherResponse

data class WeatherUiState(
    val weather: WeatherResponse? = null,
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val city: String = ""
)