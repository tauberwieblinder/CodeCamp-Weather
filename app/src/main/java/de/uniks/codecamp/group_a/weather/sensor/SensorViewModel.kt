package de.uniks.codecamp.group_a.weather.sensor

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SensorViewModel @Inject constructor(
    private val lightSensor: LightSensor,
    private val ambientTemperatureSensor: AmbientTemperatureSensor,
    private val relativeHumiditySensor: RelativeHumiditySensor,
    private val airPressureSensor: AirPressureSensor
): ViewModel() {

    val sensorList = listOf(
        lightSensor,
        ambientTemperatureSensor,
        relativeHumiditySensor,
        airPressureSensor
    )

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