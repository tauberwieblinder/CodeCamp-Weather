package de.uniks.codecamp.group_a.weather.ui.screens.weather

import android.Manifest
import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import de.uniks.codecamp.group_a.weather.viewmodel.WeatherViewModel
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalMaterialApi::class, ExperimentalPermissionsApi::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun WeatherScreen(
    weatherViewModel: WeatherViewModel,
    onNavigateToSensorScreen: () -> Unit
) {
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

    // Wir verwenden hier die Hilt Navigation Compose Library, um das ViewModel direkt in Composables zu injecten
    // Über die accompanist permission library rufen wir den aktuellen Permission state für Location ab,
    // wenn die Location Permission gegeben ist, dann lade die Wetter Daten
    val locationPermissionState = rememberPermissionState(
        permission = Manifest.permission.ACCESS_COARSE_LOCATION,
        onPermissionResult = { isGranted ->
            if (isGranted) {
                weatherViewModel.loadCurrentWeather()
            }
        }
    )

    val pullRefreshState = rememberPullRefreshState(
        refreshing = weatherViewModel.isRefreshing,
        onRefresh = {
            weatherViewModel.loadCurrentWeather()

            weatherViewModel.isRefreshing = false
        }
    )

    // Check ob Permission vorhanden, dann zeige die Wetter Übersicht
    if (locationPermissionState.status.isGranted) {
        if (weatherViewModel.weatherDataState.value.weatherDataItems.isNotEmpty()) {
            Scaffold(
                scaffoldState = scaffoldState
            ) {
                Box(
                    modifier = Modifier
                        .pullRefresh(pullRefreshState)
                        .fillMaxSize()
                ) {
                    Column(
                        horizontalAlignment = CenterHorizontally,
                        modifier = Modifier.verticalScroll(rememberScrollState())
                    ) {
                        CurrentWeatherOverview(
                            weatherViewModel = weatherViewModel,
                            onNavigateToSensorScreen = onNavigateToSensorScreen
                        )
                        Spacer(modifier = Modifier.height(100.dp))
                        ForecastOverview(weatherViewModel = weatherViewModel)
                    }
                    PullRefreshIndicator(
                        refreshing = weatherViewModel.isRefreshing,
                        state = pullRefreshState,
                        Modifier.align(Alignment.TopCenter)
                    )
                }
            }
        } else {
            ErrorScreen(error = null) {
            }
        }
    } else { // Ansonsten rufe die Permission Abfrage UI auf
        LocationPermissionScreen(permissionState = locationPermissionState)
    }
}