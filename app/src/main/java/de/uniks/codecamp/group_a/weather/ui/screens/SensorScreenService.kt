package de.uniks.codecamp.group_a.weather.ui.screens

import kotlin.math.exp

fun calcAbsoluteHumidity(t: Float, RH: Float): Float {
    val m = 17.62f
    val T_n = 243.12f
    val A = 6.112f
    return 216.7f * (RH/100 * A * exp((m * t)/ (T_n + t)))/(273.15f + t)
}