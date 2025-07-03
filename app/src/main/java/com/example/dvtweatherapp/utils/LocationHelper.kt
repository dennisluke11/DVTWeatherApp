package com.example.dvtweatherapp.utils

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.location.Geocoder
import android.location.Location
import androidx.core.content.ContextCompat
import androidx.core.content.PermissionChecker
import com.google.android.gms.location.*
import kotlinx.coroutines.suspendCancellableCoroutine
import java.io.IOException
import java.util.Locale
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import com.example.dvtweatherapp.R

class LocationPermissionDeniedException(context: Context) :
    Exception(context.getString(R.string.error_permission_denied))

class LocationUnavailableException(context: Context) :
    Exception(context.getString(R.string.error_location_unavailable))

class GeocodingFailedException(context: Context) :
    Exception(context.getString(R.string.error_geocoding_failed))

class LocationHelper(private val context: Context) {
    private val fusedLocationClient: FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(context)

    @SuppressLint("MissingPermission")
    suspend fun getCurrentLocation(): Pair<Double, Double> {
        if (ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PermissionChecker.PERMISSION_GRANTED
        ) {
            throw LocationPermissionDeniedException(context)
        }

        // Try last known location
        var location = suspendCancellableCoroutine<Location?> { cont ->
            fusedLocationClient.lastLocation
                .addOnSuccessListener { cont.resume(it) }
                .addOnFailureListener { cont.resume(null) }
        }

        // If not available, request a fresh location
        if (location == null) {
            location = suspendCancellableCoroutine { cont ->
                val locationRequest = LocationRequest.Builder(
                    Priority.PRIORITY_HIGH_ACCURACY, 1000L
                )
                    .setMaxUpdates(1)
                    .build()

                fusedLocationClient.requestLocationUpdates(
                    locationRequest,
                    object : LocationCallback() {
                        override fun onLocationResult(result: LocationResult) {
                            fusedLocationClient.removeLocationUpdates(this)
                            cont.resume(result.lastLocation)
                        }

                        override fun onLocationAvailability(availability: LocationAvailability) {
                            if (!availability.isLocationAvailable) {
                                cont.resume(null)
                            }
                        }
                    },
                    android.os.Looper.getMainLooper()
                )
            }
        }

        if (location == null) {
            throw LocationUnavailableException(context)
        }

        return Pair(location.latitude, location.longitude)
    }

    suspend fun geocodeCity(city: String): Pair<Double, Double> {
        val geocoder = Geocoder(context, Locale.getDefault())

        return suspendCancellableCoroutine { cont ->
            try {
                val addresses = geocoder.getFromLocationName(city, 1)
                val location = addresses?.firstOrNull()
                if (location != null) {
                    cont.resume(Pair(location.latitude, location.longitude))
                } else {
                    cont.resumeWithException(GeocodingFailedException(context))
                }
            } catch (e: IOException) {
                cont.resumeWithException(GeocodingFailedException(context))
            }
        }
    }
}
