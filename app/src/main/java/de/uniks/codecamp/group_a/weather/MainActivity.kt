package de.uniks.codecamp.group_a.weather

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import dagger.hilt.android.AndroidEntryPoint
import de.uniks.codecamp.group_a.weather.sensor.SensorViewModel
import de.uniks.codecamp.group_a.weather.ui.screens.WeatherScreen
import de.uniks.codecamp.group_a.weather.ui.theme.WeatherTheme
import de.uniks.codecamp.group_a.weather.viewmodel.WeatherViewModel


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private lateinit var locationPermissionRequest: ActivityResultLauncher<String>
    private val weatherViewModel: WeatherViewModel by viewModels()
    private val sensorViewModel: SensorViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // request permission to access the location
        locationPermissionRequest = registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) {
            // if successful, load weather info
            weatherViewModel.loadCurrentWeather()
        }
        locationPermissionRequest.launch(Manifest.permission.ACCESS_COARSE_LOCATION)

        setContent {
            WeatherTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    WeatherScreen(weatherViewModel = weatherViewModel)
//                    EnvironmentSensorScreen(viewModel = sensorViewModel)
                }
            }
        }
    }
}

fun Activity.recreateSmoothly() {
    finish()
    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
    startActivity(Intent(this.intent))
}