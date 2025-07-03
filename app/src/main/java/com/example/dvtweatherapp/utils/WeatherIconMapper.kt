package com.example.dvtweatherapp.utils

import com.example.dvtweatherapp.R

object WeatherIconMapper {

    fun mapWeatherConditionToIcon(weatherMain: String?, weatherDescription: String?): Int {
        return when (weatherMain) {
            "Clear" -> R.drawable.property_1_01_sun_light

            "Clouds" -> {
                when (weatherDescription) {
                    "few clouds", "scattered clouds" -> R.drawable.property_1_05_partial_cloudy_light
                    "broken clouds", "overcast clouds" -> R.drawable.property_1_07_mostly_cloud_light
                    else -> R.drawable.property_1_15_cloud_light
                }
            }

            "Rain" -> {
                when (weatherDescription) {
                    "light rain", "moderate rain" -> R.drawable.property_1_20_rain_light
                    "heavy intensity rain", "very heavy rain", "extreme rain" -> R.drawable.property_1_18_heavy_rain_light
                    else -> R.drawable.property_1_06_rainyday_light
                }
            }

            "Drizzle" -> R.drawable.property_1_24_drop_light

            "Thunderstorm" -> R.drawable.property_1_13_thunderstorm_light

            "Snow" -> {
                when (weatherDescription) {
                    "light snow", "Snow" -> R.drawable.property_1_22_snow_light
                    "heavy snow", "Heavy snow" -> R.drawable.property_1_14_heavy_snowfall_light
                    "sleet", "rain and snow" -> R.drawable.property_1_23_hailstrom_light
                    else -> R.drawable.property_1_22_snow_light
                }
            }

            "Mist", "Smoke", "Haze", "Fog" -> R.drawable.property_1_15_cloud_light

            "Dust", "Sand", "Ash" -> R.drawable.property_1_15_cloud_light

            "Squall", "Tornado" -> R.drawable.property_1_21_heavy_wind_light

            else -> R.drawable.property_1_24_drop_light // fallback icon
        }
    }
}
