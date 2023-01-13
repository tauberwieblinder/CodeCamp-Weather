package de.uniks.codecamp.group_a.weather.data.repository

import de.uniks.codecamp.group_a.weather.data.source.remote.ApiService
import de.uniks.codecamp.group_a.weather.domain.repository.Response
import de.uniks.codecamp.group_a.weather.domain.repository.WeatherRepository
import de.uniks.codecamp.group_a.weather.model.WeatherData
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val apiService: ApiService
): WeatherRepository {
    override suspend fun getCurrentWeather(
        latitude: String,
        longitude: String
    ): Response<WeatherData> {
        return try {
            Response.Success(
                data = apiService.getCurrentWeather(
                    latitude = latitude,
                    longitude = longitude
                ).convertToWeatherData(),
            )
        } catch (e: Exception) {
            e.printStackTrace()
            Response.Error(e.message ?:"An unknown error occured while fetching weather information")
        }
    }
}