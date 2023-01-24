package de.uniks.codecamp.group_a.weather.model.repository

import de.uniks.codecamp.group_a.weather.model.source.remote.ApiService
import de.uniks.codecamp.group_a.weather.domain.repository.Response
import de.uniks.codecamp.group_a.weather.domain.repository.WeatherRepositoryInterface
import de.uniks.codecamp.group_a.weather.model.WeatherData
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val apiService: ApiService
): WeatherRepositoryInterface {
    override suspend fun getCurrentWeather(
        latitude: Double,
        longitude: Double
    ): Response<WeatherData> {
        return try {
            Response.Success(
                data = apiService.getCurrentWeather(
                    latitude = latitude.toString(),
                    longitude = longitude.toString()
                ).convertToWeatherData()
            )
        } catch (e: Exception) {
            e.printStackTrace()
            Response.Error(e.message ?:"An unknown error occurred while fetching weather information")
        }
    }

    override suspend fun getForecast(
        latitude: Double,
        longitude: Double
    ): Response<List<WeatherData>> {
        return try {
            Response.Success(
                data = apiService.getForecast(
                    latitude = latitude.toString(),
                    longitude = longitude.toString()
                ).convertToWeatherDataList()
            )
        } catch (e: Exception) {
            e.printStackTrace()
            Response.Error(e.message ?:"An unknown error occurred while fetching weather information")
        }
    }
}