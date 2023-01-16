package de.uniks.codecamp.group_a.weather.viewmodel

import de.uniks.codecamp.group_a.weather.model.WeatherData

data class ForecastState(
    val weatherDataList: List<WeatherData>? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)