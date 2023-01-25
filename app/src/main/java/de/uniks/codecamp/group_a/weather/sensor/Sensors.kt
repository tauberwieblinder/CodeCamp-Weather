package de.uniks.codecamp.group_a.weather.sensor

import android.content.Context
import android.content.pm.PackageManager
import android.hardware.Sensor

class LightSensor(
    context: Context
): EnvironmentSensor(
    context = context,
    sensorFeature = PackageManager.FEATURE_SENSOR_LIGHT,
    sensorType = Sensor.TYPE_LIGHT
) {
    override fun toString(): String {
        return "Brightness"
    }
}

class AmbientTemperatureSensor(
    context: Context
): EnvironmentSensor(
    context = context,
    sensorFeature = PackageManager.FEATURE_SENSOR_AMBIENT_TEMPERATURE,
    sensorType = Sensor.TYPE_AMBIENT_TEMPERATURE
) {
    override fun toString(): String {
        return "Ambient Temperature"
    }
}

class RelativeHumiditySensor(
    context: Context
): EnvironmentSensor(
    context = context,
    sensorFeature = PackageManager.FEATURE_SENSOR_RELATIVE_HUMIDITY,
    sensorType = Sensor.TYPE_RELATIVE_HUMIDITY
) {
    override fun toString(): String {
        return "Relative Air Humidity"
    }
}

class AirPressureSensor(
    context: Context
): EnvironmentSensor(
    context = context,
    sensorFeature = PackageManager.FEATURE_SENSOR_BAROMETER,
    sensorType = Sensor.TYPE_PRESSURE
) {
    override fun toString(): String {
        return "Air Pressure"
    }
}