package com.example.dvtweatherapp.data

interface IWeatherRepository {
    suspend fun getForecastWeather(
        latitude: Double,
        longitude: Double,
        apiKey: String
    ): WeatherResponse
}
