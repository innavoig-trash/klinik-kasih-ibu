package com.example.klinikkasihibu.data.repository

import android.util.Log
import com.example.klinikkasihibu.data.model.Absen
import com.example.klinikkasihibu.data.model.dto.AbsenDto
import com.example.klinikkasihibu.util.FirestoreConfig
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

class AbsenRepository(
    private val store: FirebaseFirestore,
    private val auth: FirebaseAuth
) {
    fun observeAbsen(limit: Long? = null): Flow<List<Absen>> = callbackFlow {
        val user = auth.currentUser ?: return@callbackFlow
        val reference = store
            .collection(FirestoreConfig.ABSEN_COLLECTION)
            .whereEqualTo("userId", user.uid)
            .orderBy("date", Query.Direction.DESCENDING)

        limit?.let { reference.limit(it) }

        val registration = reference
            .addSnapshotListener { snapshot, error ->
                error?.let { trySend(emptyList()) }
                val absen = snapshot?.toObjects(AbsenDto::class.java)?.map { it.toDomain() }
                trySend(absen ?: emptyList())
            }

        awaitClose { registration.remove() }
    }

    suspend fun upsertAbsen(absen: Absen) {
        store
            .collection(FirestoreConfig.ABSEN_COLLECTION)
            .document(absen.uuid)
            .set(AbsenDto.fromDomain(absen))
            .await()
    }
}