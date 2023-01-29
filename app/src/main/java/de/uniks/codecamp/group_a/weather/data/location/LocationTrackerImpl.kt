package de.uniks.codecamp.group_a.weather.data.location

import android.Manifest
import android.app.Application
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import de.uniks.codecamp.group_a.weather.domain.location.LocationTrackerInterface
import kotlinx.coroutines.suspendCancellableCoroutine
import javax.inject.Inject
import kotlin.coroutines.resume

class LocationTrackerImpl @Inject constructor(
    private var fusedLocationClient: FusedLocationProviderClient,
    private val application: Application
): LocationTrackerInterface {
    override suspend fun getLocation(): Location? {
        // check if all necessary permissions are given
        val locationManager =
            application.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val gpsEnabled = locationManager.isProviderEnabled(Context.LOCATION_SERVICE) or
                locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)

        val locationPermission = ContextCompat.checkSelfPermission(
            application,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
        val locationPermission2 = ContextCompat.checkSelfPermission(
            application,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
        val internetPermission = ContextCompat.checkSelfPermission(
            application,
            Manifest.permission.INTERNET
        ) == PackageManager.PERMISSION_GRANTED

        if (!(gpsEnabled && (locationPermission||locationPermission2) && internetPermission)) {
            // TODO: show alert explaining what permissions have to be given in order to use the app
            return null
        }

        // transform callback to a coroutine to allow asynchronous retrieval of location
        return suspendCancellableCoroutine { continuation ->
            fusedLocationClient.lastLocation.apply {
                if (isComplete) {
                    if (isSuccessful) {
                        continuation.resume(result)
                    } else {
                        continuation.resume(null)
                    }
                    return@suspendCancellableCoroutine
                }
                addOnSuccessListener {
                    continuation.resume(it)
                }
                addOnCanceledListener {
                    continuation.cancel()
                }
            }
        }
    }
}