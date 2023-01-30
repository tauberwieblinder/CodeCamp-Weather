package de.uniks.codecamp.group_a.weather.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
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
import de.uniks.codecamp.group_a.weather.domain.repository.WeatherRepositoryInterface
import de.uniks.codecamp.group_a.weather.util.Resource
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val repository: WeatherRepositoryInterface
): ViewModel() {
    private val _weatherDataState = mutableStateOf(WeatherDataState())
    val weatherDataState: State<WeatherDataState> = _weatherDataState

    private val _eventFlow = MutableSharedFlow<UIEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    sealed class UIEvent {
        data class ShowSnackbar(val message: String) : UIEvent()
    }

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
            repository.getCurrentWeather().onEach { result ->
                when (result) {
                    is Resource.Success -> {
                        _weatherDataState.value = weatherDataState.value.copy(
                            weatherDataItems = result.data ?: emptyList(),
                            isLoading = false
                        )
                    }
                    is Resource.Error -> {
                        _weatherDataState.value = weatherDataState.value.copy(
                            weatherDataItems = result.data ?: emptyList(),
                            isLoading = false
                        )
                        _eventFlow.emit(UIEvent.ShowSnackbar("Couldn't load current weather data. Make sure to enable Location Access."))
                    }
                    is Resource.Loading -> {
                        _weatherDataState.value = weatherDataState.value.copy(
                            weatherDataItems = result.data ?: emptyList(),
                            isLoading = true
                        )
                    }
                }
            }.launchIn(this)
        }
    }
}