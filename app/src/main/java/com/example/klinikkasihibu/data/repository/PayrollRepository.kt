package com.example.klinikkasihibu.data.repository

import com.example.klinikkasihibu.data.model.Payroll
import com.example.klinikkasihibu.data.model.dto.PayrollDto
import com.example.klinikkasihibu.util.FirestoreConfig
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class PayrollRepository(
    private val store: FirebaseFirestore,
    private val auth: FirebaseAuth,
) {
    fun observePayroll(): Flow<List<Payroll>> = callbackFlow {
        val user = auth.currentUser ?: return@callbackFlow
        val reference = store
            .collection(FirestoreConfig.PAYROLL_COLLECTION)
            .whereEqualTo("userId", user.uid)
            .orderBy("createdAt", Query.Direction.DESCENDING)
        val registration = reference
            .addSnapshotListener { snapshot, error ->
                error?.let { trySend(emptyList()) }
                val payroll = snapshot?.toObjects(PayrollDto::class.java)?.map { it.toDomain() }
                trySend(payroll ?: emptyList())
            }
        awaitClose { registration.remove() }
    }
}