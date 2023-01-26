package de.uniks.codecamp.group_a.weather.sensor

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SensorViewModel @Inject constructor(
    val lightSensor: LightSensor,
    val ambientTemperatureSensor: AmbientTemperatureSensor,
    val relativeHumiditySensor: RelativeHumiditySensor,
    val airPressureSensor: AirPressureSensor
): ViewModel() {

    var brightness by mutableStateOf(0.0f)
    var temperature by mutableStateOf(0.0f)
    var relHumidity by mutableStateOf(0.0f)
    var pressure by mutableStateOf(0.0f)

    init {
        lightSensor.startListening()
        lightSensor.setOnValueChange { value ->
            brightness = value
        }

        ambientTemperatureSensor.startListening()
        ambientTemperatureSensor.setOnValueChange { value ->
            temperature = value
        }

        relativeHumiditySensor.startListening()
        relativeHumiditySensor.setOnValueChange { value ->
            relHumidity = value
        }

        airPressureSensor.startListening()
        airPressureSensor.setOnValueChange { value ->
            pressure = value
        }
    }
}