package de.uniks.codecamp.group_a.weather.ui.screens.weather

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Scale
import coil.size.Size
import de.uniks.codecamp.group_a.weather.model.WeatherData
import de.uniks.codecamp.group_a.weather.viewmodel.WeatherViewModel

@Composable
fun ForecastOverview(weatherViewModel: WeatherViewModel) {

    val state = weatherViewModel.weatherDataState
    var data = state.value.weatherDataItems
    if (data.isNotEmpty()) {
        data.subList(1, state.value.weatherDataItems.size).also { data = it }
        Column(
            modifier = Modifier
                .padding(20.dp)
                .fillMaxHeight()
                .fillMaxSize(),
            verticalArrangement = Arrangement.Bottom
        ) {
            Row {
                Column {
                    Text(
                        text = "Forecast",
                        style = MaterialTheme.typography.caption,
                        fontSize = 24.sp
                    )

                    Spacer(modifier = Modifier.height(10.dp))
                    Divider(color = Color.LightGray, thickness = 2.dp)
                }
            }
            Spacer(modifier = Modifier.height(20.dp))

            // Wenn die Daten geladen werden zeige einen Ladekreis
            if (state.value.isLoading) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    CircularProgressIndicator()
                }
            } else {
                LazyRow(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    contentPadding = PaddingValues(start = 5.dp, end = 5.dp)
                ) {
                    items(items = data, itemContent = { item ->
                        ForecastCard(weatherData = item)
                    })
                }
            }
        }

    }

}

@Composable
fun ForecastCard(weatherData: WeatherData) {

    Card(
        elevation = 8.dp,
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = weatherData.time,
                style = MaterialTheme.typography.body1,
                modifier = Modifier
                    .wrapContentSize(),
                textAlign = TextAlign.Center
            )
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
                modifier = Modifier.size(70.dp)
            )
            Text(
                text = "${weatherData.temperature}°",
                style = MaterialTheme.typography.caption,
                fontSize = 18.sp,
                modifier = Modifier
                    .wrapContentSize(),
                textAlign = TextAlign.Center
            )
        }
    }
}