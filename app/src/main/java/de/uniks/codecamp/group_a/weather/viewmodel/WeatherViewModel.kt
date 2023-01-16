package de.uniks.codecamp.group_a.weather.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import de.uniks.codecamp.group_a.weather.domain.location.LocationTrackerInterface
import de.uniks.codecamp.group_a.weather.domain.repository.Response
import de.uniks.codecamp.group_a.weather.domain.repository.WeatherRepositoryInterface
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val repository: WeatherRepositoryInterface,
    private val locationTrackerInterface: LocationTrackerInterface
): ViewModel()
{
    var currentWeatherState by mutableStateOf(WeatherState())
        private set

    var forecastState by mutableStateOf(ForecastState())
        private set

    fun loadCurrentWeatherData() {
        viewModelScope.launch {
            currentWeatherState = currentWeatherState.copy(
                isLoading = true,
                error = null
            )
            locationTrackerInterface.getLocation()?.let { location ->
                when(val result = repository.getCurrentWeather(location.latitude, location.longitude)) {
                    is Response.Success -> {
                        currentWeatherState = currentWeatherState.copy(
                            weatherData = result.data,
                            isLoading = false,
                            error = null
                        )
                    }
                    is Response.Error -> {
                        currentWeatherState = currentWeatherState.copy(
                            weatherData = null,
                            isLoading = false,
                            error = result.message
                        )
                    }
                }
            } ?: kotlin.run {
                currentWeatherState = currentWeatherState.copy(
                    isLoading = false,
                    error = "Couldn't retrieve location"
                )
            }
        }
    }

    fun loadForecast() {
        viewModelScope.launch {
            forecastState = forecastState.copy(
                isLoading = true,
                error = null
            )
            locationTrackerInterface.getLocation()?.let { location ->
                when(val result = repository.getForecast(location.latitude, location.longitude)) {
                    is Response.Success -> {
                        forecastState = forecastState.copy(
                            weatherDataList = result.data,
                            isLoading = false,
                            error = null
                        )
                    }
                    is Response.Error -> {
                        forecastState = forecastState.copy(
                            weatherDataList = null,
                            isLoading = false,
                            error = result.message
                        )
                    }
                }
            } ?: kotlin.run {
                forecastState = forecastState.copy(
                    isLoading = false,
                    error = "Couldn't retrieve location"
                )
            }
        }
    }
}