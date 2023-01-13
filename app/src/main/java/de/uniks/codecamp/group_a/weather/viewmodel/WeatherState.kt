package de.uniks.codecamp.group_a.weather.viewmodel

import de.uniks.codecamp.group_a.weather.model.WeatherData

data class WeatherState(
    val weatherData: WeatherData? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)