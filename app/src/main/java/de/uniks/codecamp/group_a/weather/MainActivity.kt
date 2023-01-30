package de.uniks.codecamp.group_a.weather

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import de.uniks.codecamp.group_a.weather.sensor.SensorViewModel
import de.uniks.codecamp.group_a.weather.ui.screens.weather.WeatherScreen
import de.uniks.codecamp.group_a.weather.ui.screens.sensor.EnvironmentSensorScreen
import de.uniks.codecamp.group_a.weather.ui.theme.WeatherTheme
import de.uniks.codecamp.group_a.weather.viewmodel.WeatherViewModel

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val weatherViewModel: WeatherViewModel by viewModels()
    private val sensorViewModel: SensorViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WeatherTheme {
                WeatherAppNavHost()
            }
        }
    }

    @Composable
    fun WeatherAppNavHost(
        modifier: Modifier = Modifier,
        navController: NavHostController = rememberNavController(),
        startDestination: String = "main"
    ) {
        NavHost(
            modifier = modifier,
            navController = navController,
            startDestination = startDestination
        ) {
            composable("main") {
                WeatherScreen(
                    weatherViewModel = weatherViewModel,
                    onNavigateToSensorScreen = {
                        navController.navigate("sensorScreen")
                    }
                )
            }
            composable("sensorScreen") {
                EnvironmentSensorScreen(sensorViewModel = sensorViewModel)
            }
        }
    }
}