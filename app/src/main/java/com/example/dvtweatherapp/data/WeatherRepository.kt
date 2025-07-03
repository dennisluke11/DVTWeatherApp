package com.example.dvtweatherapp.data

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class WeatherRepository(
    private val api: WeatherApi
) : IWeatherRepository {

    override suspend fun getForecastWeather(
        latitude: Double,
        longitude: Double,
        apiKey: String
    ): WeatherResponse = withContext(Dispatchers.IO) {
        api.getForecastWeather(
            latitude = latitude,
            longitude = longitude,
            apiKey = apiKey
        )
    }
}
