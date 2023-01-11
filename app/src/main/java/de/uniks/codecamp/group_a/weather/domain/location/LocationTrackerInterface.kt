package de.uniks.codecamp.group_a.weather.domain.location
import android.location.Location

interface LocationTrackerInterface {
    suspend fun getLocation(): Location? // could be null if location cannot be fetched
}