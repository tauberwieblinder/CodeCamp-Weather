package de.uniks.codecamp.group_a.weather.model.source.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import de.uniks.codecamp.group_a.weather.model.WeatherData

@Dao
interface WeatherDataDao {
    @Query("SELECT * FROM  weather")
    suspend fun getAllWeather(): List<WeatherData>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWeatherData(weatherData: WeatherData): Long

    @Query("DELETE FROM weather")
    suspend fun deleteAllWeather()
}