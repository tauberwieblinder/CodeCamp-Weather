package de.uniks.codecamp.group_a.weather.modules

import android.app.Application
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import de.uniks.codecamp.group_a.weather.sensor.AirPressureSensor
import de.uniks.codecamp.group_a.weather.sensor.AmbientTemperatureSensor
import de.uniks.codecamp.group_a.weather.sensor.LightSensor
import de.uniks.codecamp.group_a.weather.sensor.RelativeHumiditySensor
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SensorModule {
    @Provides
    @Singleton
    fun provideLightSensor(app: Application): LightSensor {
        return LightSensor(app)
    }

    @Provides
    @Singleton
    fun provideAmbientTemperatureSensor(app: Application): AmbientTemperatureSensor {
        return AmbientTemperatureSensor(app)
    }

    @Provides
    @Singleton
    fun provideRelativeHumiditySensor(app: Application): RelativeHumiditySensor {
        return RelativeHumiditySensor(app)
    }

    @Provides
    @Singleton
    fun provideAirPressureSensor(app: Application): AirPressureSensor {
        return AirPressureSensor(app)
    }
}