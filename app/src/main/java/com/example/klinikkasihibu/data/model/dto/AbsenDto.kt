package com.example.klinikkasihibu.data.model.dto

import com.example.klinikkasihibu.data.model.Absen
import com.example.klinikkasihibu.data.model.AbsenType
import java.util.Date

data class AbsenDto(
    // uuid format ("dateString-userId")
    val uuid: String? = null,
    val userId: String? = null,
    val dateString: String? = null,
    val type: String? = null,
    val date: Date? = null,
) {
    fun toDomain(): Absen {
        return Absen(
            uuid = uuid ?: "",
            userId = userId ?: "",
            dateString = dateString ?: "",
            type = type?.let { AbsenType.convertFromString(it) } ?: AbsenType.Izin,
            date = date ?: Date()
        )
    }

    companion object {
        fun fromDomain(absen: Absen): AbsenDto {
            return AbsenDto(
                uuid = absen.uuid,
                userId = absen.userId,
                dateString = absen.dateString,
                type = absen.type.toString(),
                date = absen.date
            )
        }
    }
}