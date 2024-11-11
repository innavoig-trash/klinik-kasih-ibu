package com.example.klinikkasihibu.data.model.dto

import com.example.klinikkasihibu.data.model.User

data class UserDto(
    val id: String? = null,
    val email: String? = null,
    val name: String? = null,
    val role: String? = null,
    val imageUrl: String? = null
) {
    fun toDomain(): User {
        return User(
            id = id ?: "",
            email = email ?: "",
            name = name ?: "",
            role = role ?: "",
            imageUrl = imageUrl
        )
    }
}