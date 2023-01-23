package de.uniks.codecamp.group_a.weather.ui.screens

import androidx.compose.runtime.Composable
import de.uniks.codecamp.group_a.weather.R
import kotlin.math.abs


@Composable
fun getLightIcon(value: Float): Int {
    var painter = R.drawable.sonne

    when {
        value < 100 -> painter = R.drawable.sonne_leer
        ((value >= 100) && (value < 1000)) -> painter = R.drawable.sonne_1_
        ((value >= 1000) && (value < 30000)) -> painter = R.drawable.sonne_2_
        value >= 30000 -> painter = R.drawable.sonne
    }

    return painter
}

@Composable
fun getTemperatureIcon(value: Float): Int {
    val min = -273
    val max = 100

    var painter = R.drawable.celsius

    when {
        value < 0 -> painter = R.drawable.celsius
        ((value >= 0) && (value < 15)) -> painter = R.drawable.celsius1
        ((value >= 15) && (value < 30)) -> painter = R.drawable.celsius2
        value >= 30 -> painter = R.drawable.celsius3
    }

    return painter
}

@Composable
fun getRelHumidityIcon(value: Float): Int {
    val min = 0
    val max = 100

    var painter = R.drawable.water

    when {
        value < 25 -> painter = R.drawable.water
        ((value >= 25) && (value < 50)) -> painter = R.drawable.water1
        ((value >= 50) && (value < 75)) -> painter = R.drawable.water2
        value >= 75 -> painter = R.drawable.water3
    }

    return painter
}

@Composable
fun getPressureIcon(value: Float): Int {
    val min = 0
    val max = 1100
    val step = abs(max - min) / 4

    var painter = R.drawable.barometer

    when {
        value < (min + step) -> painter = R.drawable.barometer
        ((value >= (min + step)) && (value < (min + step * 2))) -> painter = R.drawable.barometer1
        ((value >= (min + step * 2)) && (value < (min + step * 3))) -> painter = R.drawable.barometer2
        value >= (min + step * 3) -> painter = R.drawable.barometer3
    }

    return painter
}