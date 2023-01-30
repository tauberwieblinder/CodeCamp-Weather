package de.uniks.codecamp.group_a.weather.ui.screens.weather

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Scale
import coil.size.Size
import de.uniks.codecamp.group_a.weather.R
import de.uniks.codecamp.group_a.weather.model.WeatherData
import de.uniks.codecamp.group_a.weather.viewmodel.WeatherViewModel

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CurrentWeatherOverview(
    weatherViewModel: WeatherViewModel,
    onNavigateToSensorScreen: () -> Unit
) {
    val state = weatherViewModel.weatherDataState
    if (state.value.weatherDataItems.isNotEmpty()) {
        val weatherData: WeatherData = state.value.weatherDataItems.first()
        Column(
            modifier = Modifier
                .padding(20.dp)
        ) {
            // Wenn die Daten geladen werden zeige einen Ladekreis
            if (state.value.isLoading) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    CircularProgressIndicator()
                }
            } else {
                // Wenn Wetter Daten verfügbar sind, dann zeige den Screen
                Row {
                    Column {
                        Row {
                            Icon(Icons.Default.LocationOn, contentDescription = null,
                                Modifier
                                    .size(30.dp)
                                    .align(CenterVertically)
                            )
                            Spacer(modifier = Modifier.width(5.dp))
                            Text(
                                text = weatherData.location.toString(),
                                style = MaterialTheme.typography.caption,
                                fontSize = 30.sp,
                                modifier = Modifier
                                    .weight(1f, false)
                                    .align(CenterVertically)
                            )
                        }
                        Text(
                            text = weatherData.date,
                            style = MaterialTheme.typography.body1
                        )
                    }
                }
                Spacer(modifier = Modifier.height(20.dp))
                // Todays Weather Card
                Row {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth(),
                        elevation = 10.dp,
                        onClick = onNavigateToSensorScreen
                    ) {
                        Row(
                            modifier = Modifier
                                .padding(10.dp)
                                .fillMaxWidth(),
                            verticalAlignment = CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .weight(1f)
                                    .padding(horizontal = 5.dp)
                            ) {
                                // Wir verwenden hier den rememberAsyncImagePainer
                                // von der coil-compose Library, um das Icon aus
                                // der OpenWeather API zu laden, man könnte hier die
                                // API Url noch als Konstante hinterlegen
                                // Wenn das Icon nicht geladen werden kann könnte man noch einen Placeholder hinterlegen oder eine contentDescription
                                Image(
                                    painter = rememberAsyncImagePainter(
                                        model = ImageRequest.Builder(LocalContext.current)
                                            .data("https://openweathermap.org/img/w/${weatherData.icon}.png")
                                            .crossfade(true)
                                            .size(Size.ORIGINAL)
                                            .scale(Scale.FILL)
                                            .build(),
                                    ),
                                    contentDescription = "",
                                    contentScale = ContentScale.FillBounds,
                                    modifier = Modifier.size(100.dp)
                                )
                            }
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .weight(1f)
                                    .padding(horizontal = 5.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Column {
                                    Text(
                                        text = "${weatherData.temperature}°",
                                        style = MaterialTheme.typography.caption,
                                        fontSize = 30.sp
                                    )
                                    Text(
                                        text = weatherData.description,
                                        style = MaterialTheme.typography.caption,
                                        fontWeight = FontWeight.Medium,
                                        fontStyle = FontStyle.Italic,
                                        fontSize = 16.sp
                                    )
                                }
                            }
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .weight(1f)
                                    .padding(horizontal = 5.dp)
                            ) {
                                Column {
                                    WeatherDetailRow(
                                        iconResId = R.drawable.wind,
                                        text = "${weatherData.wind_speed} m/s"
                                    )
                                    WeatherDetailRow(
                                        iconResId = R.drawable.water3,
                                        text = "${weatherData.humidity} %"
                                    )
                                    WeatherDetailRow(
                                        title = "H: ",
                                        text = "${weatherData.temperature_max}°"
                                    )
                                    WeatherDetailRow(
                                        title = "T: ",
                                        text = "${weatherData.temperature_min}°"
                                    )
                                }
                            }
                            Box(
                                modifier = Modifier
                                    .weight(0.5f),
                                contentAlignment = Alignment.CenterEnd
                            ) {
                                Image(
                                    painter = painterResource(id = R.drawable.forward),
                                    contentDescription = "",
                                    modifier = Modifier
                                        .height(20.dp)
                                        .padding(end = 5.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun WeatherDetailRow(@DrawableRes iconResId: Int? = null, title: String? = null, text: String) {
    Row(
        verticalAlignment = CenterVertically
    ) {
        iconResId?.let {
            Image(
                painter = painterResource(id = it),
                contentDescription = "",
                modifier = Modifier
                    .height(12.dp)
                    .padding(end = 5.dp)
            )
        }

        title?.let {
            Text(
                text = it,
                style = MaterialTheme.typography.body1,
                fontSize = 14.sp
            )
        }

        Text(
            text = text,
            style = MaterialTheme.typography.body1,
            fontSize = 14.sp
        )
    }
}