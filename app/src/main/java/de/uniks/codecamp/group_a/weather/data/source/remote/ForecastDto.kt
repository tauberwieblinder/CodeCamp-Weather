package de.uniks.codecamp.group_a.weather.data.source.remote

import com.google.gson.annotations.SerializedName
import de.uniks.codecamp.group_a.weather.model.WeatherData
import java.util.*

class ForecastDto(
    @SerializedName("list")
    private val weatherDtoList: List<WeatherDto>
) {
    fun convertToWeatherDataList(): List<WeatherData> {
        val weatherDataList = mutableListOf<WeatherData>()
        for(weatherDto in weatherDtoList) {
            weatherDataList.add(weatherDto.convertToWeatherData())
        }
        return weatherDataList
    }
}