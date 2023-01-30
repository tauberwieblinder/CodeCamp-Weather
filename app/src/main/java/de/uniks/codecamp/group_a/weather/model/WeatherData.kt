package de.uniks.codecamp.group_a.weather.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "weather")
data class WeatherData(
    @PrimaryKey
    val date: String,
    val time: String,
    val temperature: Int,
    val location: String?, // e.g. "Kassel"
    val description: String, // e.g. "Partly Cloudy"
    val temperature_max: Int,
    val temperature_min: Int,
    val humidity: Int,
    val wind_speed: Double,
    val icon: String
)