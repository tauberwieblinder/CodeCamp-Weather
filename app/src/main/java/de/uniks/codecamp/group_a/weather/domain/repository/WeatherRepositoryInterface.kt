package de.uniks.codecamp.group_a.weather.domain.repository

import de.uniks.codecamp.group_a.weather.model.WeatherData

interface WeatherRepositoryInterface {
    suspend fun getCurrentWeather(latitude: Double, longitude: Double): Response<WeatherData>
}