package com.example.klinikkasihibu.data.model.dto

import com.example.klinikkasihibu.data.model.Cuti
import com.example.klinikkasihibu.data.model.CutiStatus
import java.util.Date

data class CutiDto(
    val uuid: String? = null,
    val userId: String? = null,
    val username: String? = null,
    val userImageUrl: String? = null,
    val role: String? = null,
    val startDate: Date? = null,
    val endDate: Date? = null,
    val status: String? = null,
    val category: String? = null,
    val description: String? = null,
    val documentUrl: String? = null,
    val createdAt: Date? = null,
    val updatedAt: Date? = null
) {
    fun toDomain(): Cuti {
        return Cuti(
            uuid = uuid ?: "",
            userId = userId ?: "",
            username = username ?: "",
            userImageUrl = userImageUrl,
            role = role ?: "",
            startDate = startDate ?: Date(),
            endDate = endDate ?: Date(),
            status = status?.let { CutiStatus.valueOf(it) } ?: CutiStatus.Menunggu,
            category = category ?: "",
            description = description ?: "",
            documentUrl = documentUrl ?: "",
            createdAt = createdAt ?: Date(),
            updatedAt = updatedAt
        )
    }

    companion object {
        fun fromDomain(cuti: Cuti): CutiDto {
            return CutiDto(
                uuid = cuti.uuid,
                userId = cuti.userId,
                username = cuti.username,
                userImageUrl = cuti.userImageUrl,
                role = cuti.role,
                startDate = cuti.startDate,
                endDate = cuti.endDate,
                status = cuti.status.toString(),
                category = cuti.category,
                description = cuti.description,
                documentUrl = cuti.documentUrl,
                createdAt = cuti.createdAt,
                updatedAt = cuti.updatedAt
            )
        }
    }
}