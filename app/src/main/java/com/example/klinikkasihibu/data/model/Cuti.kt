package com.example.klinikkasihibu.data.model

import java.util.Date
import java.util.concurrent.TimeUnit

data class Cuti(
    val uuid: String,
    val userId: String,
    val username: String,
    val userImageUrl: String?,
    val role: String,
    val startDate: Date,
    val endDate: Date,
    val status: CutiStatus,
    val category: String,
    val description: String,
    val documentUrl: String,
    val createdAt: Date,
    val updatedAt: Date?
) {
    val duration: Long = TimeUnit.DAYS.convert(endDate.time - startDate.time, TimeUnit.MILLISECONDS) + 1
}

enum class CutiStatus {
    Menunggu,
    Diterima,
    Ditolak;
}

val cutiCategoryList = listOf(
    "Cuti Tahunan",
    "Cuti Kesehatan",
    "Cuti Sakit",
    "Cuti Kelahiran",
    "Cuti karena alasan penting",
    "Cuti Bersama",
    "Cuti di luar tanggungan",
)