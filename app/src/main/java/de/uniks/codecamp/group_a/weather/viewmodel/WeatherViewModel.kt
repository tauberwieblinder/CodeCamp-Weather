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
    var state by mutableStateOf(WeatherState())
        private set

    fun loadCurrentWeatherData() {
        viewModelScope.launch {
            state = state.copy(
                isLoading = true,
                error = null
            )
            locationTrackerInterface.getLocation()?.let { location ->
                when(val result = repository.getCurrentWeather(location.latitude, location.longitude)) {
                    is Response.Success -> {
                        state = state.copy(
                            weatherData = result.data,
                            isLoading = false,
                            error = null
                        )
                    }
                    is Response.Error -> {
                        state = state.copy(
                            weatherData = null,
                            isLoading = false,
                            error = result.message
                        )
                    }
                }
            } ?: kotlin.run {
                state = state.copy(
                    isLoading = false,
                    error = "Couldn't retrieve location"
                )
            }
        }
    }
}