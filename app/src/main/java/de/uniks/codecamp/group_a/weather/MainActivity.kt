package de.uniks.codecamp.group_a.weather

import android.Manifest

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import dagger.hilt.android.AndroidEntryPoint
import de.uniks.codecamp.group_a.weather.model.WeatherData
import de.uniks.codecamp.group_a.weather.ui.theme.WeatherTheme
import de.uniks.codecamp.group_a.weather.viewmodel.WeatherViewModel
import androidx.compose.material.Text
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.ui.Alignment
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Scale
import coil.size.Size
import com.google.accompanist.permissions.*
import de.uniks.codecamp.group_a.weather.ui.screens.EnvironmentSensorScreen

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            WeatherTheme {
                WeatherAppNavHost()
            }
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
            MainScreen(
                onNavigateToSensorScreen = {
                    navController.navigate("sensorScreen")
                }
            )
        }
        composable("sensorScreen") {
            EnvironmentSensorScreen()
        }
    }
}


@OptIn(ExperimentalPermissionsApi::class, ExperimentalMaterialApi::class)
@Composable
fun MainScreen(viewModel: WeatherViewModel = hiltViewModel(), onNavigateToSensorScreen: () -> Unit) {
    // Wir verwenden hier die Hilt Navigation Compose Library, um das ViewModel direkt in Composables zu injecten

    // Über die accompanist permission library rufen wir den aktuellen Permission state für Location ab,
    // wenn die Location Permission gegeben ist, dann lade die Wetter Daten
    val locationPermissionState = rememberPermissionState(
        permission = Manifest.permission.ACCESS_COARSE_LOCATION,
        onPermissionResult = { isGranted ->
            if (isGranted) {
                viewModel.loadAll()
            }
        }
    )

    val pullRefreshState = rememberPullRefreshState(
        refreshing = viewModel.isRefreshing,
        onRefresh = {
            viewModel.loadAll()
            viewModel.isRefreshing = false
        }
    )

    if (locationPermissionState.status.isGranted) { // Check ob Permission vorhanden, dann zeige die Wetter Übersicht
        Surface(
            modifier = Modifier.fillMaxSize()
        ) {
            Box(
                Modifier
                    .pullRefresh(pullRefreshState)
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
            ) {
                Column() {
                    CurrentWeatherOverview(viewModel = viewModel, onNavigateToSensorScreen)
                    ForecastOverview(viewModel = viewModel)
                }
                PullRefreshIndicator(
                    refreshing = viewModel.isRefreshing,
                    state = pullRefreshState,
                    Modifier.align(Alignment.TopCenter)
                )
            }
        }
    } else { // Ansonsten rufe die Permission Abfrage UI auf
        LocationPermissionScreen(permissionState = locationPermissionState)
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CurrentWeatherOverview(viewModel: WeatherViewModel, onNavigateToSensorScreen: () -> Unit) {

    val state = viewModel.currentWeatherState
    val data = state.data

    Column(
        modifier = Modifier
            .padding(20.dp)
    ) {

        // Wenn die Daten geladen werden zeige einen Ladekreis
        if (state.isLoading) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                CircularProgressIndicator()
            }
        } else {

            // Wenn Wetter Daten verfügbar sind, dann zeige den Screen
            data?.let { weatherData ->

                Row {
                    Column {
                        Row {
                            Image(
                                painter = painterResource(id = R.drawable.location_icon),
                                contentDescription = "",
                                modifier = Modifier
                                    .size(32.dp)
                                    .align(Alignment.CenterVertically)
                            )
                            Spacer(modifier = Modifier.width(5.dp))
                            Text(
                                text = weatherData.location.toString(),
                                style = MaterialTheme.typography.caption,
                                fontSize = 30.sp,
                                modifier = Modifier
                                    .weight(1f, false)
                                    .align(Alignment.CenterVertically)
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
                            verticalAlignment = Alignment.CenterVertically
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
                                        text = "${weatherData.description}",
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
        } ?: run { // ansonsten zeige eine Errorseite

            ErrorScreen(
                error = state.error,
                onRefresh = {
                    viewModel.loadCurrentWeather()
                }
            )
        }
    }
}

@Composable
fun ForecastOverview(viewModel: WeatherViewModel) {

    val state = viewModel.forecastState
    val data = state.data

    Column(
        modifier = Modifier
            .padding(20.dp)
    ) {

        Row {
            Column {
                Text(
                    text = "Heute",
                    style = MaterialTheme.typography.caption,
                    fontSize = 24.sp
                )

                Spacer(modifier = Modifier.height(10.dp))
                Divider(color = Color.LightGray, thickness = 2.dp)
            }
        }
        Spacer(modifier = Modifier.height(20.dp))

        // Wenn die Daten geladen werden zeige einen Ladekreis
        if (state.isLoading) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                CircularProgressIndicator()
            }
        } else {

            data?.let {

                LazyRow(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    contentPadding = PaddingValues(start = 5.dp, end = 5.dp)
                ) {
                    items(items = data, itemContent = { item ->
                        ForecastCard(weatherData = item)
                    })
                }

            } ?: run {
                ErrorScreen(
                    error = state.error,
                    onRefresh = {
                        viewModel.loadCurrentWeather()
                    }
                )
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

@Composable
fun ErrorScreen(error: String?, onRefresh: () -> Unit) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = "Beim Laden der Daten ist ein Fehler aufgetreten. Lade die Seite erneut.")
        error?.let { // Wenn wir einen error Text haben, zeigen wir diesen zusätzlich an
            Text(text = "Fehler: ${it}")
        }
        OutlinedButton( // Mit Klick auf den Button wird versucht die Wetterdaten erneut abzurufen
            onClick = onRefresh
        ) {
            Text("Neu laden")
        }
    }
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun LocationPermissionScreen(permissionState: PermissionState) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(15.dp),
        elevation = 10.dp,
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(20.dp)
        ) {

            Image(
                painter = painterResource(id = R.drawable.sunny),
                "",
            )

            Text(
                text = "CodeCamp Wetter App",
                fontSize = 30.sp
            )

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "Diese App benötigt die Berechtigung, deinen Standort abzufragen.",
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(15.dp))

            Button(onClick = {
                permissionState.launchPermissionRequest()
            }) {
                Text("Standort freigeben")
            }
        }
    }
}

@Composable
fun WeatherDetailRow(@DrawableRes iconResId: Int? = null, title: String? = null, text: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically
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