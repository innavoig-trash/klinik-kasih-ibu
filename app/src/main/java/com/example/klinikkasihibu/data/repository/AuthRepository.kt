package com.example.klinikkasihibu.data.repository

import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.tasks.await

class AuthRepository(
    private val auth: FirebaseAuth
) {
    suspend fun login(email: String, password: String) {
        auth
            .signInWithEmailAndPassword(email, password)
            .await() ?: throw Exception("Authentication failed")
    }
}