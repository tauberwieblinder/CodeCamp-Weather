package de.uniks.codecamp.group_a.weather.viewmodel

import android.app.Application
import android.content.Context
import android.location.Location
import android.location.LocationManager
import androidx.compose.runtime.*
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.accompanist.permissions.PermissionState
import dagger.hilt.android.internal.Contexts.getApplication
import dagger.hilt.android.lifecycle.HiltViewModel
import de.uniks.codecamp.group_a.weather.domain.location.LocationTrackerInterface
import de.uniks.codecamp.group_a.weather.domain.repository.Response
import de.uniks.codecamp.group_a.weather.domain.repository.WeatherRepositoryInterface
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val repository: WeatherRepositoryInterface,
    private val locationTrackerInterface: LocationTrackerInterface
): ViewModel() {

    var currentWeatherState by mutableStateOf(WeatherState())
        private set

    var forecastState by mutableStateOf(ForecastState())
        private set

    var isRefreshing by mutableStateOf(false)

    init { // Initially load weather data
        loadAll()
    }

    fun loadAll() {
        loadCurrentWeather()
        loadForecast()
    }

    fun loadCurrentWeather() {
        viewModelScope.launch {
            currentWeatherState = currentWeatherState.copy(
                isLoading = true,
                error = null
            )
            locationTrackerInterface.getLocation()?.let { location ->
                when(val result = repository.getCurrentWeather(location.latitude, location.longitude)) {
                    is Response.Success -> {
                        currentWeatherState = currentWeatherState.copy(
                            data = result.data,
                            isLoading = false,
                            error = null
                        )
                    }
                    is Response.Error -> {
                        currentWeatherState = currentWeatherState.copy(
                            data = null,
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
                            data = result.data,
                            isLoading = false,
                            error = null
                        )
                    }
                    is Response.Error -> {
                        forecastState = forecastState.copy(
                            data = null,
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