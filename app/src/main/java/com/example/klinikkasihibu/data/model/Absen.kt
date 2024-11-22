package com.example.klinikkasihibu.data.model

import java.util.Date
import java.util.UUID

data class Absen(
    val uuid: String,
    val userId: String,
    val username: String,
    val role: String,
    val dateString: String,
    val type: AbsenType,
    val date: Date,
)