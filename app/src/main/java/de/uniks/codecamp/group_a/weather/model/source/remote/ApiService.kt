package de.uniks.codecamp.group_a.weather.model.source.remote

import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("2.5/weather?appid=b646030374fca52ef7f0b40e8987aca0&units=metric")
    suspend fun getCurrentWeather(
        @Query("lat") latitude: String,
        @Query("lon") longitude: String
    ): WeatherDto

    @GET("2.5/forecast?appid=b646030374fca52ef7f0b40e8987aca0&units=metric&cnt=8")
    suspend fun getForecast(
        @Query("lat") latitude: String,
        @Query("lon") longitude: String
    ): ForecastDto

}