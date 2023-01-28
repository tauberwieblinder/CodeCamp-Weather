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
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dagger.hilt.android.AndroidEntryPoint
import de.uniks.codecamp.group_a.weather.model.WeatherData
import de.uniks.codecamp.group_a.weather.sensor.SensorViewModel
import de.uniks.codecamp.group_a.weather.ui.theme.WeatherTheme
import de.uniks.codecamp.group_a.weather.viewmodel.WeatherViewModel
import androidx.compose.material.Text
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import de.uniks.codecamp.group_a.weather.data.source.remote.ForecastDto
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private lateinit var locationPermissionRequest: ActivityResultLauncher<String>
    private val viewModel: WeatherViewModel by viewModels()
    private val sensorViewModel: SensorViewModel by viewModels()
    private var data: WeatherData? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val data = getData()
        setContent {
            WeatherTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    MainScreen(data = data)
                }
            }
        }
    }
    fun getData(): WeatherData?{
        // request permission to access the location, load the weather information
        locationPermissionRequest = registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) {
            viewModel.loadCurrentWeather()
        }
        locationPermissionRequest.launch(Manifest.permission.ACCESS_COARSE_LOCATION)

        if (viewModel.currentWeatherState.data != null) {
            data = viewModel.currentWeatherState.data
        } else {
            // Etwas ist beim Laden schiefgelaufen
        }
        return data
    }
}
@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    WeatherTheme {
        var data: WeatherData? = null
        MainScreen(data)
    }
}
fun Activity.recreateSmoothly() {
    finish()
    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
    startActivity(Intent(this.intent))
}
@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun MainScreen(data: WeatherData?) {

    val scope = rememberCoroutineScope()
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colors.background
    ) {
        Column {
            Row() {

                Column {
                    Text(text = data?.location.toString())
                    //data?.location.toString()
                    Text(text = "Donnerstag")
                    //data?.let { Text(text = it.time) }
                }
                Spacer(Modifier.weight(1f))

                IconButton(
                    modifier = Modifier.size(24.dp),
                    onClick = {

                        scope.launch {

                        }
                    }
                ) {
                    Icon(
                        Icons.Filled.Refresh,
                        "contentDescription",
                    )
                }
            }

            Row {
                //Image(
                //    painter = painterResource(R.drawable.profile_picture),
                //    contentDescription = "Contact profile picture",
                //)

                if (true ){
                    Image(
                        painter = painterResource(id = R.drawable.sunny),//ChoseImage(weatherDes = data.description),
                        contentDescription =" Contact sunny picture")
                }
                Column {
                    Text("16", fontSize = 100.sp)
                    //text = data?.temperature.toString()
                    Text("  Sonnig", fontSize = 30.sp)
                    //text = data?.description.toString()
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
                            painter = painterResource(id = R.drawable.sunny),
                            contentDescription =" Contact sunny picture",Modifier.size(20.dp))
                        Text(text = data?.wind_speed.toString())
                    }
                    Row {

                        Image(
                            painter = painterResource(id = R.drawable.sunny),
                            contentDescription =" Contact sunny picture",Modifier.size(20.dp))
                        Text(text = data?.humidity.toString())

                    }
                    Row {

                        Text(text = "H: ")
                        Text(text = data?.temperature_max.toString())
                    }
                    Row {
                        Text(text = "T: ")
                        Text(text = data?.temperature_min.toString())
                    }
                }
            }

            Text("Heute")
            Divider(color = Color.Black, thickness = 1.dp)
            LazyRow {
                // Add a single item
                for(i in 23 downTo 0){
                    item {
                        Column() {
                            Text(text = "12:00")
                            Image(
                                painter = painterResource(id = R.drawable.sunny),
                                contentDescription =" Contact sunny picture",Modifier.size(70.dp))
                            Text(text = "16°C", fontSize = 25.sp)
                        }
                    }
                }

            }
        }
    }
}
@Composable
fun ChoseImage(weatherDes: String) : Painter {
    if (weatherDes.equals("clear sky")) {
        return painterResource(id = R.drawable.sunny)
    }
    if (weatherDes.equals("few clouds")) {
        return painterResource(id = R.drawable.sunny)
    }
    if (weatherDes.equals("scattered clouds")) {
        return painterResource(id = R.drawable.sunny)
    }
    if (weatherDes.equals("broken clouds")) {
        return painterResource(id = R.drawable.sunny)
    }
    if (weatherDes.equals("shower rain")) {
        return painterResource(id = R.drawable.sunny)
    }
    if (weatherDes.equals("rain")) {
        return painterResource(id = R.drawable.sunny)
    }
    if (weatherDes.equals("thunderstorm")) {
        return painterResource(id = R.drawable.sunny)
    }
    if (weatherDes.equals("snow")) {
        return painterResource(id = R.drawable.sunny)
    }
    if (weatherDes.equals("mist")) {
        return painterResource(id = R.drawable.sunny)
    }
    return painterResource(id = R.drawable.sunny)
}
fun forecastList(): List<WeatherData>{
    return emptyList()
}