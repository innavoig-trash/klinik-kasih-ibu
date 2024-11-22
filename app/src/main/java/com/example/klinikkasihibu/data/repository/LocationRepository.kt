package com.example.klinikkasihibu.data.repository

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import com.example.klinikkasihibu.data.model.Location
import com.example.klinikkasihibu.data.model.dto.LocationDto
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import kotlin.math.PI
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.pow
import kotlin.math.sin
import kotlin.math.sqrt

class LocationRepository(
    private val context: Context,
    private val store: FirebaseFirestore,
) {
    suspend fun fetchLocation(): Location? {
        val location = store
            .collection("location")
            .document("zjxfwn1ufRAP1IyN9pTh")
            .get()
            .await()
            .toObject(LocationDto::class.java)
            ?.toDomain()
        return location
    }

    @SuppressLint("MissingPermission")
    suspend fun isLocationCorrect(): Boolean {
        return withContext(Dispatchers.IO) {
            val fusedLocationClient: FusedLocationProviderClient =
                LocationServices.getFusedLocationProviderClient(context)
            val lastLocation = fusedLocationClient.lastLocation.await()
            val intendedLocation = fetchLocation()
            if (lastLocation == null || intendedLocation == null) return@withContext false
            isLocationWithinRadius(
                lat1 = lastLocation.latitude,
                lon1 = lastLocation.longitude,
                lat2 = intendedLocation.latitude,
                lon2 = intendedLocation.longitude,
                radiusInMeters = intendedLocation.radius
            )
        }
    }

    private fun isLocationWithinRadius(lat1: Double, lon1: Double, lat2: Double, lon2: Double, radiusInMeters: Double): Boolean {
        // Convert latitude and longitude to radians
        val lat1Rad = lat1.toRadians()
        val lon1Rad = lon1.toRadians()
        val lat2Rad = lat2.toRadians()
        val lon2Rad = lon2.toRadians()

        // Haversine formula to calculate distance between two points on a sphere
        val dLon = lon2Rad - lon1Rad
        val dLat = lat2Rad - lat1Rad
        val a = sin(dLat / 2).pow(2) + cos(lat1Rad) * cos(lat2Rad) * sin(dLon / 2).pow(2)
        val c = 2 * atan2(sqrt(a), sqrt(1 - a))

        // Radius of the Earth in meters
        val earthRadius = 6371000.0

        // Calculate distance in meters
        val distanceInMeters = earthRadius * c

        // Check if distance is within radius
        return distanceInMeters <= radiusInMeters
    }

    // Extension function to convert degrees to radians
    private fun Double.toRadians(): Double = this * PI / 180
}