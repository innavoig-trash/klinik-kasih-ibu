package com.example.klinikkasihibu.data.model.dto

import com.example.klinikkasihibu.data.model.Cuti
import com.example.klinikkasihibu.data.model.CutiStatus
import java.util.Date

data class CutiDto(
    val uuid: String? = null,
    val userId: String? = null,
    val startDate: Date? = null,
    val endDate: Date? = null,
    val status: String? = null,
    val category: String? = null,
    val documentUrl: String? = null,
) {
    fun toDomain(): Cuti {
        return Cuti(
            uuid = uuid ?: "",
            userId = userId ?: "",
            startDate = startDate ?: Date(),
            endDate = endDate ?: Date(),
            status = status?.let { CutiStatus.valueOf(it) } ?: CutiStatus.Menunggu,
            category = category ?: "",
            documentUrl = documentUrl ?: ""
        )
    }

    companion object {
        fun fromDomain(cuti: Cuti): CutiDto {
            return CutiDto(
                uuid = cuti.uuid,
                userId = cuti.userId,
                startDate = cuti.startDate,
                endDate = cuti.endDate,
                status = cuti.status.toString(),
                category = cuti.category,
                documentUrl = cuti.documentUrl
            )
        }
    }
}