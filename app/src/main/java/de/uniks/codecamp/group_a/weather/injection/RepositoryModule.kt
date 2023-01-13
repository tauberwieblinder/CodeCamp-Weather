package de.uniks.codecamp.group_a.weather.injection

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import de.uniks.codecamp.group_a.weather.data.repository.WeatherRepositoryImpl
import de.uniks.codecamp.group_a.weather.domain.repository.WeatherRepositoryInterface
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun bindWeatherRepository(
        weatherRepositoryImpl: WeatherRepositoryImpl
    ): WeatherRepositoryInterface
}