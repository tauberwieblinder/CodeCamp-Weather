package de.uniks.codecamp.group_a.weather

import android.Manifest
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import de.uniks.codecamp.group_a.weather.ui.theme.WeatherTheme

class MainActivity : ComponentActivity() {
    private lateinit var locationPermissionRequest: ActivityResultLauncher<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // request permission to access the location, load the weather information
        locationPermissionRequest = registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) {
            // TODO: load the weather information from the viewmodel
        }
        locationPermissionRequest.launch(Manifest.permission.ACCESS_COARSE_LOCATION)

        setContent {
            WeatherTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Greeting("Android")
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    WeatherTheme {
        Greeting("Android")
    }
}