package de.uniks.codecamp.group_a.weather

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import dagger.hilt.android.AndroidEntryPoint
import de.uniks.codecamp.group_a.weather.ui.screens.EnvironmentSensorScreen
import de.uniks.codecamp.group_a.weather.ui.theme.WeatherTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WeatherTheme {
                EnvironmentSensorScreen()
            }
        }
    }
}