package de.uniks.codecamp.group_a.weather.model

data class WeatherData(
    val time: String,
    val temperature: Double,
    val location: String?, // e.g. "Kassel"
    val description: String, // e.g. "Partly Cloudy"
    val temperature_max: Double,
    val temperature_min: Double,
    val humidity: Int,
    val wind_speed: Double
)
