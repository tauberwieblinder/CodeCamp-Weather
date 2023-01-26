package de.uniks.codecamp.group_a.weather.domain.repository

import de.uniks.codecamp.group_a.weather.model.WeatherData
import kotlinx.coroutines.flow.Flow

interface WeatherRepositoryInterface {
    suspend fun getCurrentWeather(latitude: Double, longitude: Double): Flow<WeatherData>

    suspend fun insertWeatherData(weatherData: WeatherData)

    suspend fun deleteWeatherData(weatherData: WeatherData)

    suspend fun getForecast(latitude: Double, longitude: Double): Flow<List<WeatherData>>

    suspend fun insertForecast(forecast: List<WeatherData>)

    suspend fun deleteForecast(forecast: List<WeatherData>)
}