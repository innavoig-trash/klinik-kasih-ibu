package com.example.klinikkasihibu.data.model.dto

import com.example.klinikkasihibu.data.model.Location

data class LocationDto(
    val latitude: Double? = null,
    val longitude: Double? = null,
    val radius: Double? = null,
) {
    fun toDomain(): Location {
        return Location(
            latitude = latitude ?: 0.0,
            longitude = longitude ?: 0.0,
            radius = radius ?: 0.0,
        )
    }
}