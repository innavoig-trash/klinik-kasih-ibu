package com.example.klinikkasihibu.data.repository

import android.net.Uri
import com.example.klinikkasihibu.data.model.User
import com.example.klinikkasihibu.data.model.dto.UserDto
import com.example.klinikkasihibu.util.FirestoreConfig
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

class UserRepository(
    private val store: FirebaseFirestore,
    private val storage: FirebaseStorage,
    private val auth: FirebaseAuth
) {
    fun observeCurrentUser(): Flow<User?> =  callbackFlow {
        val user = auth.currentUser ?: return@callbackFlow
        val reference = store
            .collection(FirestoreConfig.USER_COLLECTION)
            .document(user.uid)

        val registration = reference
            .addSnapshotListener { snapshot, error ->
                error?.let { trySend(null) }
                val domain = snapshot?.toObject(UserDto::class.java)?.toDomain()
                trySend(domain)
            }

        awaitClose { registration.remove() }
    }

    suspend fun upsertUser(user: User) {
        val reference = store
            .collection(FirestoreConfig.USER_COLLECTION)
            .document(user.id)
        reference.set(user).await()
    }

    suspend fun uploadUserImage(uri: Uri): String {
        val storageRef = storage.reference
        val docRef = storageRef.child("user/${auth.currentUser?.uid}")
        val uploadTask = docRef.putFile(uri)
        val urlTask = uploadTask.continueWithTask {
            val downloadUrl = docRef.downloadUrl
            return@continueWithTask downloadUrl
        }.await()
        return urlTask.toString()
    }
}