package com.example.klinikkasihibu.ui.route.main.home

import com.example.klinikkasihibu.data.model.User
import com.google.firebase.auth.FirebaseUser

data class HomeState(
    val locationPermissionGranted: Boolean = false,
    val user: User? = null,
    val isHadirLoading: Boolean = false
)