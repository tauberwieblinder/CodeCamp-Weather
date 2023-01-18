package de.uniks.codecamp.group_a.weather.viewmodel

import de.uniks.codecamp.group_a.weather.model.WeatherData

data class WeatherState(
    val data: WeatherData? = null,
    var isLoading: Boolean = false,
    var error: String? = null
)

data class ForecastState(
    val data: List<WeatherData>? = null,
    var isLoading: Boolean = false,
    var error: String? = null
)

