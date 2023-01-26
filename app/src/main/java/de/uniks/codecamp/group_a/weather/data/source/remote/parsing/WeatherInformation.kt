package de.uniks.codecamp.group_a.weather.data.source.remote.parsing

data class WeatherInformation(
    val temp: Double,
    val temp_min: Double,
    val temp_max: Double,
    val pressure: Double,
    val humidity: Double
)