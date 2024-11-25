package com.example.klinikkasihibu.ui.route.main.home

import com.example.klinikkasihibu.data.model.Location
import com.example.klinikkasihibu.data.model.User
import com.google.firebase.auth.FirebaseUser

data class HomeState(
    val locationPermissionGranted: Boolean = false,
    val user: User? = null,
    val location: Location? = null,
    val isHadirLoading: Boolean = false,
    val isAlreadyPresent: Boolean = false,
    val shouldNavigateToLeave: Boolean = false,
)