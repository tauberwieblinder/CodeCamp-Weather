package de.uniks.codecamp.group_a.weather.data.source.remote

import com.google.gson.annotations.SerializedName
import de.uniks.codecamp.group_a.weather.data.source.remote.parsing.WeatherDescription
import de.uniks.codecamp.group_a.weather.data.source.remote.parsing.WeatherInformation
import de.uniks.codecamp.group_a.weather.data.source.remote.parsing.Wind
import de.uniks.codecamp.group_a.weather.model.WeatherData
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.roundToInt

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
        val weather = weatherDescription.first()
        return WeatherData(
            date = getDateString(dt, SimpleDateFormat("EEEE, dd. MMMM yyyy", Locale.getDefault())),
            time = getDateString(dt, SimpleDateFormat("HH:mm", Locale.getDefault())),
            temperature = weatherInformation.temp.roundToInt(),
            location = name,
            description = weather.description,
            temperature_max = weatherInformation.temp_max.roundToInt(),
            temperature_min = weatherInformation.temp_min.roundToInt(),
            humidity = weatherInformation.humidity.toInt(),
            wind_speed = wind.speed,
            icon = weather.icon
        )
    }
}

