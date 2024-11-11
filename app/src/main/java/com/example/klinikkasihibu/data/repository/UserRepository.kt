package com.example.klinikkasihibu.data.repository

import com.example.klinikkasihibu.data.model.User
import com.example.klinikkasihibu.data.model.dto.UserDto
import com.example.klinikkasihibu.util.FirestoreConfig
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class UserRepository(
    private val store: FirebaseFirestore,
    private val auth: FirebaseAuth
) {
    suspend fun fetchCurrentUser(): User? {
        val firebaseUser = auth.currentUser ?: return null
        val reference = store
            .collection(FirestoreConfig.USER_COLLECTION)
            .document(firebaseUser.uid)
            .get()
            .await() ?: return null
        return reference.toObject(UserDto::class.java)?.toDomain()
    }
}