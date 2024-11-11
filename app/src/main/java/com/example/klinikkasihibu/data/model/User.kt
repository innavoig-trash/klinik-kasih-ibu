package com.example.klinikkasihibu.data.model

data class User(
    val id: String,
    val email: String,
    val name: String,
    val role: String,
    val imageUrl: String?
)