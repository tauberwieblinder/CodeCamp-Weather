package de.uniks.codecamp.group_a.weather.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import de.uniks.codecamp.group_a.weather.R
import de.uniks.codecamp.group_a.weather.model.WeatherData
import de.uniks.codecamp.group_a.weather.viewmodel.WeatherViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlin.math.roundToInt

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
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colors.background
        ) {
            if (state.weatherDataItems.isNotEmpty()) {
                val weatherData: WeatherData = state.weatherDataItems.first()
                val forecastData: List<WeatherData> =
                    state.weatherDataItems.subList(1, state.weatherDataItems.lastIndex)
                Column() {
                    CurrentWeather(weatherData = weatherData, weatherViewModel = weatherViewModel)
                    Forecast(forecastData = forecastData)
                }
            } else {
                RefreshButton(weatherViewModel = weatherViewModel)
            }
        }
    }
}

@Composable
fun CurrentWeather(weatherData: WeatherData, weatherViewModel: WeatherViewModel) {
    Column {
        Row {
            Column {
                Text(text = weatherData.location ?: "")
                Text(text = weatherData.time)
            }
            Spacer(Modifier.weight(1f))
            RefreshButton(weatherViewModel)
        }
        Row {
            Image(
                painter = choseImage(weatherDes = weatherData.description),
                contentDescription = null
            )
            Column {
                // round the double to an Int for clean representation
                Text(
                    text = weatherData.temperature.roundToInt().toString(),
                    fontSize = 100.sp
                )
                Text(text = weatherData.description, fontSize = 30.sp)
            }
            Column() {
                Row() {
                    Text(text = "")
                }
                Row() {
                    Text(text = "")
                }
                Row {
                    Image(
                        // TODO: Wind Icon
                        painter = painterResource(id = R.drawable.sunny),
                        modifier = Modifier.size(20.dp),
                        contentDescription = null
                    )
                    Text(text = weatherData.wind_speed.toString())
                }
                Row {
                    Image(
                        painter = painterResource(id = R.drawable.water3),
                        contentDescription = null,
                        Modifier.size(20.dp)
                    )
                    Text(text = weatherData.humidity.toString())

                }
                Row {
                    Text(text = "H: ")
                    Text(text = weatherData.temperature_max.toString())
                }
                Row {
                    Text(text = "L: ")
                    Text(text = weatherData.temperature_min.toString())
                }
            }
        }
    }
}

@Composable
fun Forecast(forecastData: List<WeatherData>) {
    Text("Forecast")
    Divider(color = Color.Black, thickness = 1.dp)
    LazyRow {
        for (forecastItem in forecastData) {
            item {
                Column() {
                    Text(text = forecastItem.time)
                    Image(
                        painter = choseImage(weatherDes = forecastItem.description),
                        contentDescription = null,
                        Modifier.size(70.dp)
                    )
                    Text(text = forecastItem.temperature.toString(), fontSize = 25.sp)
                }
            }
        }
    }
}

@Composable
fun RefreshButton(weatherViewModel: WeatherViewModel) {
    IconButton(
        modifier = Modifier.size(24.dp),
        onClick = {
            weatherViewModel.loadCurrentWeather()
        }
    ) {
        Icon(
            Icons.Filled.Refresh,
            contentDescription = stringResource(R.string.refresh)
        )
    }
}

@Composable
fun choseImage(weatherDes: String): Painter {
    if (weatherDes == "clear sky") {
        return painterResource(id = R.drawable.sunny)
    }
    if (weatherDes == "few clouds") {
        return painterResource(id = R.drawable.sunny)
    }
    if (weatherDes == "scattered clouds") {
        return painterResource(id = R.drawable.sunny)
    }
    if (weatherDes == "broken clouds") {
        return painterResource(id = R.drawable.sunny)
    }
    if (weatherDes == "shower rain") {
        return painterResource(id = R.drawable.sunny)
    }
    if (weatherDes == "rain") {
        return painterResource(id = R.drawable.sunny)
    }
    if (weatherDes == "thunderstorm") {
        return painterResource(id = R.drawable.sunny)
    }
    if (weatherDes == "snow") {
        return painterResource(id = R.drawable.sunny)
    }
    if (weatherDes == "mist") {
        return painterResource(id = R.drawable.sunny)
    }
    return painterResource(id = R.drawable.sunny)
}

