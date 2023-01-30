package de.uniks.codecamp.group_a.weather.model.repository

import de.uniks.codecamp.group_a.weather.domain.location.LocationTrackerInterface
import de.uniks.codecamp.group_a.weather.domain.repository.WeatherRepositoryInterface
import de.uniks.codecamp.group_a.weather.model.WeatherData
import de.uniks.codecamp.group_a.weather.model.source.local.WeatherDataDao
import de.uniks.codecamp.group_a.weather.model.source.remote.ApiService
import de.uniks.codecamp.group_a.weather.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val dao: WeatherDataDao,
    private val locationTrackerInterface: LocationTrackerInterface
) : WeatherRepositoryInterface {

    override fun getCurrentWeather(): Flow<Resource<List<WeatherData>>> = flow {
        val location = locationTrackerInterface.getLocation()
        emit(Resource.Loading())

        val weatherData = dao.getAllWeather()
        emit(Resource.Loading(data = weatherData))

        try {
            val remoteCurrentWeatherData = apiService.getCurrentWeather(location?.latitude.toString(), location?.longitude.toString())
            val remoteForecastData = apiService.getForecast(location?.latitude.toString(), location?.longitude.toString())
            dao.deleteAllWeather()
            dao.insertWeatherData(remoteCurrentWeatherData.convertToWeatherData())
            remoteForecastData.convertToWeatherDataList().onEach { remoteForecast ->
                dao.insertWeatherData(remoteForecast)
            }
        } catch (e: HttpException) {
            emit(Resource.Error(throwable = e, data = weatherData))
        } catch (e: IOException) {
            emit(Resource.Error(throwable = e, data = weatherData))
        }
        val newWeatherData = dao.getAllWeather()
        emit(Resource.Success(newWeatherData))
    }
}