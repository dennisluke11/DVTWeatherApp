package com.example.dvtweatherapp.ui.weather.componets

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import com.example.dvtweatherapp.R
import com.example.dvtweatherapp.utils.Dimens
import com.example.dvtweatherapp.utils.FlowConstants.getHour
import com.example.dvtweatherapp.utils.WeatherIconMapper.mapWeatherConditionToIcon
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlin.math.abs

@Composable
fun WeatherForecastScreen(
    viewModel: WeatherViewModel,
    onRequestLocationPermission: () -> Unit,
) {
    val uiState by viewModel.uiState.collectAsState()
    var permissionRequested by remember { mutableStateOf(false) }

    var showDialog by remember { mutableStateOf(false) }
    var dialogTitle by remember { mutableStateOf("") }
    var dialogMessage by remember { mutableStateOf("") }
    val errorTitle = stringResource(id = R.string.error_title)
    val unknownErrorMessage = stringResource(id = R.string.unknown_error_message)
    val noDataTitle = stringResource(id = R.string.weather_data_title)
    val noDataMessage = stringResource(id = R.string.weather_data_message)
    val okButtonText = stringResource(id = R.string.ok_button)

    LaunchedEffect(permissionRequested) {
        if (!permissionRequested) {
            onRequestLocationPermission()
            permissionRequested = true
        }
    }

    LaunchedEffect(Unit) {
        viewModel.loadWeatherFromCurrentLocation()
    }

    LaunchedEffect(uiState.errorMessage) {
        if (uiState.errorMessage != null) {
            dialogTitle = errorTitle
            dialogMessage = uiState.errorMessage ?: unknownErrorMessage
            showDialog = true
        }
    }

    // Observe empty data
    LaunchedEffect(uiState.weather, uiState.isLoading) {
        if (uiState.weather?.list.isNullOrEmpty() && !uiState.isLoading) {
            dialogTitle = noDataTitle
            dialogMessage = noDataMessage
            showDialog = true
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF0F0F0))
            .padding(Dimens.ScreenPadding)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(Dimens.SpacerHeightLarge))

            Text(
                text = if (uiState.city.isNotBlank()) "${uiState.city} - 5 Day Forecast" else "5 Day Forecast",
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentWidth(Alignment.CenterHorizontally)
                    .padding(
                        vertical = Dimens.ForecastTitleVerticalPadding,
                        horizontal = Dimens.ForecastTitleHorizontalPadding
                    )
            )

            Spacer(modifier = Modifier.height(Dimens.SpacerHeightMedium))

            SearchInput(
                modifier = Modifier.fillMaxWidth(),
                onSearch = { cityName ->
                    viewModel.searchWeatherByCityName(cityName)
                }
            )

            Spacer(modifier = Modifier.height(Dimens.SpacerHeightMedium))

            when {
                uiState.isLoading -> {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
                }

                uiState.weather?.list?.isNotEmpty() == true -> {
                    uiState.weather?.let { weatherData ->
                        val dailyForecasts = weatherData.list
                            .groupBy {
                                SimpleDateFormat("EEEE", Locale.getDefault())
                                    .format(Date(it.dt * 1000))
                            }
                            .mapValues { (_, items) ->
                                items.minByOrNull { abs(getHour(it.dt) - 12) }
                                    ?: items.first()
                            }
                            .values
                            .sortedBy { it.dt }
                            .take(5)

                        LazyColumn(
                            modifier = Modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.spacedBy(Dimens.ForecastListItemSpacing),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            items(dailyForecasts.toList()) { forecastItem ->
                                val dayOfWeek = SimpleDateFormat("EEEE", Locale.getDefault())
                                    .format(Date(forecastItem.dt * 1000))
                                val temperature = "${(forecastItem.main.temp - 273.15).toInt()}°C"
                                val weatherMain = forecastItem.weather.firstOrNull()?.main
                                val weatherDescription =
                                    forecastItem.weather.firstOrNull()?.description
                                val iconResId = mapWeatherConditionToIcon(
                                    weatherMain,
                                    weatherDescription
                                )

                                WeatherCard(
                                    dayOfWeek = dayOfWeek,
                                    temperature = temperature,
                                    iconResId = iconResId
                                )
                            }
                        }
                    }
                }
            }
        }

        if (showDialog) {
            AlertDialogComponet(
                openDialog = showDialog,
                onDismiss = {
                    showDialog = false
                    viewModel.clearError()
                },
                title = dialogTitle,
                message = dialogMessage,
                confirmText = okButtonText
            )
        }
    }
}
