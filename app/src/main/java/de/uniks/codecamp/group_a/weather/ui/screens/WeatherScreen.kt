package de.uniks.codecamp.group_a.weather.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
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
                    val weatherData = state.weatherDataItems.last()
                    Text(text = weatherData.location?:"no location for you")
                    Text(text = weatherData.temperature.toString())
                }

            }
        }
    }
}