package de.uniks.codecamp.group_a.weather.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import de.uniks.codecamp.group_a.weather.model.WeatherData
import de.uniks.codecamp.group_a.weather.viewmodel.WeatherViewModel
import kotlinx.coroutines.flow.collectLatest

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun WeatherScreen(
    weatherViewModel: WeatherViewModel
) {
    val state = weatherViewModel.weatherDataState.value
    val scaffoldState = rememberScaffoldState()

    LaunchedEffect(key1 = true) {
        weatherViewModel.eventFlow.collectLatest { event ->
            when (event) {
                is WeatherViewModel.UIEvent.ShowSnackbar -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = event.message
                    )
                }
            }
        }
    }
    Scaffold(
        scaffoldState = scaffoldState
    ) {
        Box(
            modifier = Modifier.background(MaterialTheme.colors.background)
        ) {
            Column(modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
            ) {
                if (state.weatherDataItems.isNotEmpty()) {
                    val weatherData: WeatherData = state.weatherDataItems.first()
                    val forecastData: List<WeatherData> = state.weatherDataItems.subList(1, state.weatherDataItems.lastIndex)
                    Column() {
                        Weather(weatherData = weatherData)
                        Row() {
                            forecastData.forEach { forecastData ->
                                Weather(weatherData = forecastData)
                            }
                        }
                    }
                }

            }
        }
    }
}

@Composable
fun Weather(weatherData: WeatherData) {
    Column() {
    Text(text = weatherData.location?:"")
    Text(text = weatherData.description)
    Text(text = weatherData.temperature.toString())
    Text(text = weatherData.temperature_min.toString())
    Text(text = weatherData.temperature_max.toString())
    Text(text = weatherData.humidity.toString())
    Text(text = weatherData.time)
    }
}
