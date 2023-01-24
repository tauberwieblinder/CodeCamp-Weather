package de.uniks.codecamp.group_a.weather.model.source.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import de.uniks.codecamp.group_a.weather.model.WeatherData
import kotlinx.coroutines.flow.Flow

@Dao
interface WeatherDataDao {

    @Query("SELECT * FROM  weather")
    fun getCurrentWeather(): Flow<List<WeatherData>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWeatherData(weatherData: WeatherData)

    @Delete
    suspend fun deleteWeatherData(weatherData: WeatherData)
}