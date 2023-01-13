package de.uniks.codecamp.group_a.weather.domain.repository

import de.uniks.codecamp.group_a.weather.model.WeatherData

interface WeatherRepository {
    suspend fun getCurrentWeather(latitude: String, longitude: String): Response<WeatherData>
}