package com.example.klinikkasihibu.di

import android.content.Context
import com.example.klinikkasihibu.data.repository.AbsenRepository
import com.example.klinikkasihibu.data.repository.AuthRepository
import com.example.klinikkasihibu.data.repository.CutiRepository
import com.example.klinikkasihibu.data.repository.LocationRepository
import com.example.klinikkasihibu.data.repository.PayrollRepository
import com.example.klinikkasihibu.data.repository.UserRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Singleton
    @Provides
    fun provideAuthRepository(
        auth: FirebaseAuth
    ): AuthRepository {
        return AuthRepository(auth)
    }

    @Singleton
    @Provides
    fun provideAbsenRepository(
        store: FirebaseFirestore,
        auth: FirebaseAuth
    ): AbsenRepository {
        return AbsenRepository(store, auth)
    }

    @Singleton
    @Provides
    fun provideUserRepository(
        store: FirebaseFirestore,
        storage: FirebaseStorage,
        auth: FirebaseAuth
    ): UserRepository {
        return UserRepository(store, storage, auth)
    }

    @Singleton
    @Provides
    fun provideCutiRepository(
        store: FirebaseFirestore,
        auth: FirebaseAuth,
        storage: FirebaseStorage
    ): CutiRepository {
        return CutiRepository(store, auth, storage)
    }

    @Singleton
    @Provides
    fun provideLocationRepository(
        @ApplicationContext context: Context,
        store: FirebaseFirestore,
    ): LocationRepository {
        return LocationRepository(context, store)
    }

    @Singleton
    @Provides
    fun providePayrollRepository(
        store: FirebaseFirestore,
        auth: FirebaseAuth,
    ): PayrollRepository {
        return PayrollRepository(store, auth)
    }
}