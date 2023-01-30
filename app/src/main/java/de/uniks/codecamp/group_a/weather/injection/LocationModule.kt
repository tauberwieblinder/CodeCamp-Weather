package de.uniks.codecamp.group_a.weather.injection

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import de.uniks.codecamp.group_a.weather.model.location.LocationTrackerImpl
import de.uniks.codecamp.group_a.weather.domain.location.LocationTrackerInterface
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class LocationModule {
    @Binds
    @Singleton
    abstract fun bindLocationTracker(locationTrackerImpl: LocationTrackerImpl): LocationTrackerInterface
}