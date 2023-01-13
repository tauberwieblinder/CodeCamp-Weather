package de.uniks.codecamp.group_a.weather.data.source.remote

import com.google.gson.annotations.SerializedName
import de.uniks.codecamp.group_a.weather.model.WeatherData
import java.text.SimpleDateFormat
import java.util.*

class CurrentWeatherDto(
    @SerializedName("weather")
    private val weatherDescription: List<WeatherDescription>,
    @SerializedName("main")
    private val weatherInformation: WeatherInformation,
    private val wind: Wind,
    private val dt: Long,
    private val name: String
) {
    fun convertToWeatherData(): WeatherData {
        return WeatherData(
            time = SimpleDateFormat("dd/M/yyyy hh:mm:ss", Locale.getDefault()).format(dt),
            temperature = weatherInformation.temp,
            location = name,
            description = weatherDescription[0].description,
            temperature_max = weatherInformation.temp_max,
            temperature_min = weatherInformation.temp_min,
            humidity = weatherInformation.humidity.toInt(),
            wind_speed = wind.speed.toDouble()
        )
    }
}

data class WeatherInformation(
    val temp: Double,
    val feels_like: Double,
    val temp_min: Double,
    val temp_max: Double,
    val pressure: Double,
    val humidity: Double
)

data class WeatherDescription(
    val id: Int,
    val main: String,
    val description: String,
    val icon: String
)

data class Wind(
    val speed: Int,
    val deg: Int
)