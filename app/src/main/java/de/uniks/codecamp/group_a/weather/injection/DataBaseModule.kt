package de.uniks.codecamp.group_a.weather.injection

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import de.uniks.codecamp.group_a.weather.model.source.local.WeatherDataBase
import de.uniks.codecamp.group_a.weather.model.source.local.WeatherDataDao
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataBaseModule {
    @Provides
    fun provideWeatherDataDao(dataBase: WeatherDataBase): WeatherDataDao {
        return dataBase.weatherDataDao
    }

    @Provides
    @Singleton
    fun provideWeatherDataBase(@ApplicationContext appContext: Context): WeatherDataBase {
        return WeatherDataBase.invoke(appContext)
    }
}