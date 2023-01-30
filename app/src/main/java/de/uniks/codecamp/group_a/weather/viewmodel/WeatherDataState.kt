package de.uniks.codecamp.group_a.weather.viewmodel

import de.uniks.codecamp.group_a.weather.model.WeatherData

data class WeatherDataState(
    val weatherDataItems: List<WeatherData> = emptyList(),
    val isLoading: Boolean = false
    )
