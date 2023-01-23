package de.uniks.codecamp.group_a.weather.ui.screens

import android.hardware.Sensor
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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

    var caption = ""
    var unit = ""
    var resId = R.drawable.sonne_leer
    when {
        sensor.sensorType == Sensor.TYPE_LIGHT -> {
            caption = sensor.toString()
            unit = "lux"
            resId = getLightIcon(value = value)
        }
        sensor.sensorType == Sensor.TYPE_AMBIENT_TEMPERATURE -> {
            caption = sensor.toString()
            unit = "°C"
            resId = getTemperatureIcon(value = value)
        }
        sensor.sensorType == Sensor.TYPE_RELATIVE_HUMIDITY -> {
            caption = sensor.toString()
            unit = "%"
            resId = getRelHumidityIcon(value = value)
        }
        sensor.sensorType == Sensor.TYPE_PRESSURE -> {
            caption = sensor.toString()
            unit = "hPa"
            resId = getPressureIcon(value = value)
        }
    }

    Text(text = caption, fontSize = 35.sp, textAlign = TextAlign.Start)
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.End,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = "$value $unit", fontSize = 25.sp)
        Image(painter = painterResource(id = resId), contentDescription = null, modifier = Modifier.size(50.dp))
    }
}