package de.uniks.codecamp.group_a.weather.model.source.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import de.uniks.codecamp.group_a.weather.model.WeatherData

@Database(entities = [WeatherData::class], version = 1, exportSchema = false)
abstract class WeatherDataBase : RoomDatabase() {
    abstract val weatherDataDao: WeatherDataDao

    companion object {
        // make sure there is only one instance of this database
        @Volatile
        private var instance: WeatherDataBase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: createDataBase(context).also { instance = it }
        }

        private fun createDataBase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                WeatherDataBase::class.java,
                "weather_db.db"
            ).build()
    }
}