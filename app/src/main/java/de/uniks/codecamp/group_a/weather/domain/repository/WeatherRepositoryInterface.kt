package de.uniks.codecamp.group_a.weather.domain.repository

import de.uniks.codecamp.group_a.weather.model.WeatherData
import de.uniks.codecamp.group_a.weather.util.Resource
import kotlinx.coroutines.flow.Flow

interface WeatherRepositoryInterface {
    fun getCurrentWeather(): Flow<Resource<List<WeatherData>>>
}