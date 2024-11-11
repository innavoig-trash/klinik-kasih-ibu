package com.example.klinikkasihibu.data.repository

import android.net.Uri
import android.util.Log
import com.example.klinikkasihibu.data.model.Absen
import com.example.klinikkasihibu.data.model.Cuti
import com.example.klinikkasihibu.data.model.dto.AbsenDto
import com.example.klinikkasihibu.data.model.dto.CutiDto
import com.example.klinikkasihibu.util.FirestoreConfig
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

class CutiRepository(
    private val store: FirebaseFirestore,
    private val auth: FirebaseAuth,
    private val storage: FirebaseStorage
) {
    fun observeCuti(limit: Long? = null): Flow<List<Cuti>> = callbackFlow {
        val user = auth.currentUser ?: return@callbackFlow
        val reference = store
            .collection(FirestoreConfig.CUTI_COLLECTION)
            .whereEqualTo("userId", user.uid)
            .orderBy("startDate", Query.Direction.DESCENDING)

        limit?.let { reference.limit(it) }

        val registration = reference
            .addSnapshotListener { snapshot, error ->
                error?.let { trySend(emptyList()) }
                val absen = snapshot?.toObjects(CutiDto::class.java)?.map { it.toDomain() }
                trySend(absen ?: emptyList())
            }

        awaitClose { registration.remove() }
    }

    suspend fun upsertCuti(cuti: Cuti) {
        store
            .collection(FirestoreConfig.CUTI_COLLECTION)
            .document(cuti.uuid)
            .set(CutiDto.fromDomain(cuti))
            .await()
    }

    suspend fun uploadCutiDocument(
        cutiUUID: String,
        file: Uri
    ): String {
        val storageRef = storage.reference
        val docRef = storageRef.child("cuti/$cutiUUID.pdf")
        val uploadTask = docRef.putFile(file)
        val urlTask = uploadTask.continueWithTask {
            val downloadUrl = docRef.downloadUrl
            return@continueWithTask downloadUrl
        }.await()
        return urlTask.toString()
    }
}