package de.uniks.codecamp.group_a.weather.data.source.remote

import com.google.gson.annotations.SerializedName
import de.uniks.codecamp.group_a.weather.data.source.remote.parsing.WeatherDescription
import de.uniks.codecamp.group_a.weather.data.source.remote.parsing.WeatherInformation
import de.uniks.codecamp.group_a.weather.data.source.remote.parsing.Wind
import de.uniks.codecamp.group_a.weather.model.WeatherData
import java.text.SimpleDateFormat
import java.util.*

class WeatherDto(
    @SerializedName("weather")
    private val weatherDescription: List<WeatherDescription>,
    @SerializedName("main")
    private val weatherInformation: WeatherInformation,
    private val wind: Wind,
    private val dt: Long,
    private val name: String?
) {
    private fun getDateString(time: Long, simpleDateFormat: SimpleDateFormat) : String = simpleDateFormat.format(time * 1000L)

    fun convertToWeatherData(): WeatherData {
        return WeatherData(
            time = getDateString(dt, SimpleDateFormat("dd MMMM yyyy, HH:mm:ss", Locale.getDefault())),
            temperature = weatherInformation.temp,
            location = name,
            description = weatherDescription[0].description,
            temperature_max = weatherInformation.temp_max,
            temperature_min = weatherInformation.temp_min,
            humidity = weatherInformation.humidity.toInt(),
            wind_speed = wind.speed
        )
    }
}

