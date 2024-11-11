package com.example.klinikkasihibu.data.model

import java.util.Date
import java.util.concurrent.TimeUnit

data class Cuti(
    val uuid: String,
    val userId: String,
    val startDate: Date,
    val endDate: Date,
    val status: CutiStatus,
    val category: String,
    val documentUrl: String,
) {
    val duration: Long = TimeUnit.DAYS.convert(endDate.time - startDate.time, TimeUnit.MILLISECONDS)
}

enum class CutiStatus {
    Menunggu,
    Disetujui,
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