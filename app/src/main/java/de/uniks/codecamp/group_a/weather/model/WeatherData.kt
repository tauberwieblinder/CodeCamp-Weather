package de.uniks.codecamp.group_a.weather.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "weather")
data class WeatherData(
    @PrimaryKey
    val time: String,

    val temperature: Double,
    val location: String?, // e.g. "Kassel"
    val description: String, // e.g. "Partly Cloudy"
    val temperature_max: Double,
    val temperature_min: Double,
    val humidity: Int,
    val wind_speed: Double
)
