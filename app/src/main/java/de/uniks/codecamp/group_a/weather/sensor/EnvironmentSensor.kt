package de.uniks.codecamp.group_a.weather.sensor

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager

abstract class EnvironmentSensor(
    private val context: Context,
    private val sensorFeature: String,
    val sensorType: Int
): SensorEventListener {

    private var onValueChange: ((Float) -> Unit)? = null

    val sensorExists: Boolean
        get() = context.packageManager.hasSystemFeature(sensorFeature)

    private val sensorManager: SensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
    private var sensor: Sensor? = sensorManager.getDefaultSensor(sensorType)

    override fun onSensorChanged(event: SensorEvent?) {
        if (!sensorExists || event == null) {
            return
        }

        onValueChange?.invoke(event.values[0])
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) = Unit

    fun startListening() {
        if (!sensorExists) {
            return
        }

        sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL)
    }

    fun stopListening() {
        if (!sensorExists) {
            return
        }

        sensorManager.unregisterListener(this)
    }

    fun setOnValueChange(listener: ((Float) -> Unit)) {
        onValueChange = listener
    }
}