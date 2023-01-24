package de.uniks.codecamp.group_a.weather.model.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import de.uniks.codecamp.group_a.weather.model.WeatherData

@Database(entities = [WeatherData::class], version = 1)
abstract class WeatherDataBase : RoomDatabase() {
    abstract val weatherDataDao: WeatherDataDao
}