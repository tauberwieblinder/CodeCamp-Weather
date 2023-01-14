package de.uniks.codecamp.group_a.weather.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import de.uniks.codecamp.group_a.weather.R
import de.uniks.codecamp.group_a.weather.sensor.EnvironmentSensor
import de.uniks.codecamp.group_a.weather.sensor.SensorViewModel
import kotlin.math.abs

@Composable
fun EnvironmentSensorScreen(modifier: Modifier = Modifier) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = { TopAppBar(title = { Text(text = "Environment Sensors") }) }
    ) {
        Surface(
            modifier = modifier
                .fillMaxSize()
                .padding(it),
            color = MaterialTheme.colors.background
        ) {
            val sensorViewModel = viewModel<SensorViewModel>()
            val sensorList = sensorViewModel.sensorList
            Column(
                modifier = modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState())
                    .padding(5.dp)
            ) {
                SensorEntry(sensor = sensorList[0], value = sensorViewModel.brightness)
                SensorEntry(sensor = sensorList[1], value = sensorViewModel.temperature)
                SensorEntry(sensor = sensorList[2], value = sensorViewModel.relHumidity)

                if (sensorList[1].sensorExists && sensorList[2].sensorExists) {
                    val absHumidity = calcAbsoluteHumidity(sensorViewModel.temperature, sensorViewModel.relHumidity)
                    Text(text = "Absolute Humidity", fontSize = 35.sp , modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Start)
                    Text(text = "$absHumidity g/m³", fontSize = 25.sp, modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.End)
                }

                SensorEntry(sensor = sensorList[3], value = sensorViewModel.pressure)
            }
        }
    }
}

@Composable
fun SensorEntry(sensor: EnvironmentSensor, value: Float) {
    if (!sensor.sensorExists) {
        return
    }

    var unit = ""
    when {
        sensor.toString().equals("Brightness/Illuminance") -> unit = "lux"
        sensor.toString().equals("Ambient Temperature") -> unit = "°C"
        sensor.toString().equals("Relative Air Humidity") -> unit = "%"
        sensor.toString().equals("Air Pressure") -> unit = "hPa"
    }

    Text(text = sensor.toString(), fontSize = 35.sp, textAlign = TextAlign.Start)
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.End,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = "$value $unit", fontSize = 25.sp)
        when {
            sensor.toString().equals("Brightness/Illuminance") -> PaintIconLight(value = value)
            sensor.toString().equals("Ambient Temperature") -> PaintIconTemp(value = value)
            sensor.toString().equals("Relative Air Humidity") -> PaintIconRelHumidity(value = value)
            sensor.toString().equals("Air Pressure") -> PaintIconPressure(value = value)
        }

    }
}

@Composable
fun PaintIconLight(value: Float) {
    val min = 0
    val max = 40000
    val step = abs(max - min) / 4

    var painter: Painter = painterResource(id = R.drawable.sonne)

    when {
        value < (min + step) -> painter = painterResource(id = R.drawable.sonne_leer)
        ((value >= (min + step)) && (value < (min + step * 2))) -> painter = painterResource(id = R.drawable.sonne_1_)
        ((value >= (min + step * 2)) && (value < (min + step * 3))) -> painter = painterResource(id = R.drawable.sonne_2_)
        value >= (min + step * 3) -> painter = painterResource(id = R.drawable.sonne)
    }

    Image(painter = painter, contentDescription = null, Modifier.size(50.dp))
}

@Composable
fun PaintIconTemp(value: Float) {
    val min = -273
    val max = 100
    val step = abs(max - min) / 4

    var painter: Painter = painterResource(id = R.drawable.celsius)

    when {
        value < (min + step) -> painter = painterResource(id = R.drawable.celsius)
        ((value >= (min + step)) && (value < (min + step * 2))) -> painter = painterResource(id = R.drawable.celsius1)
        ((value >= (min + step * 2)) && (value < (min + step * 3))) -> painter = painterResource(id = R.drawable.celsius2)
        value >= (min + step * 3) -> painter = painterResource(id = R.drawable.celsius3)
    }

    Image(painter = painter, contentDescription = null, Modifier.size(50.dp))
}

@Composable
fun PaintIconRelHumidity(value: Float) {
    val min = 0
    val max = 100
    val step = abs(max - min) / 4

    var painter: Painter = painterResource(id = R.drawable.water)

    when {
        value < (min + step) -> painter = painterResource(id = R.drawable.water)
        ((value >= (min + step)) && (value < (min + step * 2))) -> painter = painterResource(id = R.drawable.water1)
        ((value >= (min + step * 2)) && (value < (min + step * 3))) -> painter = painterResource(id = R.drawable.water2)
        value >= (min + step * 3) -> painter = painterResource(id = R.drawable.water3)
    }

    Image(painter = painter, contentDescription = null, Modifier.size(50.dp))
}

@Composable
fun PaintIconPressure(value: Float) {
    val min = 0
    val max = 1100
    val step = abs(max - min) / 4

    var painter: Painter = painterResource(id = R.drawable.barometer)

    when {
        value < (min + step) -> painter = painterResource(id = R.drawable.barometer)
        ((value >= (min + step)) && (value < (min + step * 2))) -> painter = painterResource(id = R.drawable.barometer1)
        ((value >= (min + step * 2)) && (value < (min + step * 3))) -> painter = painterResource(id = R.drawable.barometer2)
        value >= (min + step * 3) -> painter = painterResource(id = R.drawable.barometer3)
    }

    Image(painter = painter, contentDescription = null, Modifier.size(50.dp))
}