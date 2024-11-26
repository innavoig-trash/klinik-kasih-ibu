package com.example.klinikkasihibu.ui.route.main.profile

data class EditProfileState(
    val message: String? = null,
    val name: String = "",
    val image: Any? = null,
    val role: String = "",
    val isLoading: Boolean = false
)