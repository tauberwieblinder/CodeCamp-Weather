package de.uniks.codecamp.group_a.weather.ui.screens.sensor

import de.uniks.codecamp.group_a.weather.R
import kotlin.math.abs
import kotlin.math.exp

fun calcAbsoluteHumidity(t: Float, RH: Float): Float {
    val m = 17.62f
    val tn = 243.12f
    val a = 6.112f
    return 216.7f * (RH/100 * a * exp((m * t)/ (tn + t)))/(273.15f + t)
}

fun getLightIcon(value: Float): Int {
    /** min = 0
     *   max = 40000 */

    var painter = R.drawable.sonne

    when {
        value < 100 -> painter = R.drawable.sonne_leer
        ((value >= 100) && (value < 1000)) -> painter = R.drawable.sonne_1_
        ((value >= 1000) && (value < 30000)) -> painter = R.drawable.sonne_2_
        value >= 30000 -> painter = R.drawable.sonne
    }

    return painter
}

fun getTemperatureIcon(value: Float): Int {
    /** min = -273
    *   max = 100 */

    var painter = R.drawable.celsius

    when {
        value < 0 -> painter = R.drawable.celsius
        ((value >= 0) && (value < 15)) -> painter = R.drawable.celsius1
        ((value >= 15) && (value < 30)) -> painter = R.drawable.celsius2
        value >= 30 -> painter = R.drawable.celsius3
    }

    return painter
}

fun getRelHumidityIcon(value: Float): Int {
    /** min = 0
     *  max = 100 */

    var painter = R.drawable.water

    when {
        value < 25 -> painter = R.drawable.water
        ((value >= 25) && (value < 50)) -> painter = R.drawable.water1
        ((value >= 50) && (value < 75)) -> painter = R.drawable.water2
        value >= 75 -> painter = R.drawable.water3
    }

    return painter
}

fun getPressureIcon(value: Float): Int {
    /** min = 0
     *  max = 1100 */

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