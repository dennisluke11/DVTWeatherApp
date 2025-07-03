package com.example.dvtweatherapp.ui.weather.componets

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dvtweatherapp.data.IWeatherRepository
import com.example.dvtweatherapp.utils.FlowConstants
import com.example.dvtweatherapp.utils.GeocodingFailedException
import com.example.dvtweatherapp.utils.LocationHelper
import com.example.dvtweatherapp.utils.LocationPermissionDeniedException
import com.example.dvtweatherapp.utils.LocationUnavailableException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class WeatherViewModel(
    private val repository: IWeatherRepository,
    private val locationHelper: LocationHelper
) : ViewModel() {

    private val _uiState = MutableStateFlow(WeatherUiState())
    val uiState: StateFlow<WeatherUiState> = _uiState

    fun loadWeatherFromCurrentLocation() {
        viewModelScope.launch {
            if (FlowConstants.API_KEY.isBlank()) {
                _uiState.value = _uiState.value.copy(
                    errorMessage = "API Key is missing. Please provide a valid API key.",
                    isLoading = false
                )
                return@launch
            }

            _uiState.value = _uiState.value.copy(
                isLoading = true,
                errorMessage = null,
                weather = null,
                city = ""
            )

            try {
                val (latitude, longitude) = locationHelper.getCurrentLocation()

                val weatherResponse =
                    repository.getForecastWeather(latitude, longitude, FlowConstants.API_KEY)

                _uiState.value = _uiState.value.copy(
                    weather = weatherResponse,
                    city = weatherResponse.city.name,
                    isLoading = false,
                    errorMessage = null
                )

            } catch (e: LocationPermissionDeniedException) {
                _uiState.value = _uiState.value.copy(
                    errorMessage = "Location permission denied",
                    isLoading = false
                )
            } catch (e: LocationUnavailableException) {
                _uiState.value = _uiState.value.copy(
                    errorMessage = "Location unavailable",
                    isLoading = false
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    errorMessage = e.message ?: "Unknown error",
                    isLoading = false
                )
            }
        }
    }

    fun searchWeatherByCityName(city: String) {
        viewModelScope.launch {
            if (FlowConstants.API_KEY.isBlank()) {
                _uiState.value = _uiState.value.copy(
                    errorMessage = "API Key is missing. Please provide a valid API key.",
                    isLoading = false
                )
                return@launch
            }

            if (city.isBlank()) {
                _uiState.value = _uiState.value.copy(
                    errorMessage = "City name cannot be empty",
                    isLoading = false
                )
                return@launch
            }

            _uiState.value = _uiState.value.copy(
                isLoading = true,
                errorMessage = null,
                weather = null,
                city = ""
            )

            try {
                val (latitude, longitude) = locationHelper.geocodeCity(city)

                val weatherResponse =
                    repository.getForecastWeather(latitude, longitude, FlowConstants.API_KEY)

                _uiState.value = _uiState.value.copy(
                    weather = weatherResponse,
                    city = weatherResponse.city.name,
                    isLoading = false,
                    errorMessage = null
                )

            } catch (e: GeocodingFailedException) {
                _uiState.value = _uiState.value.copy(
                    errorMessage = "Failed to get coordinates for \"$city\".",
                    isLoading = false
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    errorMessage = "Failed to fetch weather data for \"$city\".",
                    isLoading = false
                )
            }
        }
    }

    fun clearError() {
        _uiState.value = _uiState.value.copy(errorMessage = null)
    }
}
