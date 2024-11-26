package com.example.klinikkasihibu.ui.route.main.notif

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.klinikkasihibu.data.repository.AbsenRepository
import com.example.klinikkasihibu.data.repository.CutiRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class NotificationViewModel @Inject constructor(
    private val absenRepository: AbsenRepository,
    private val cutiRepository: CutiRepository
): ViewModel() {
    private val _absen = absenRepository.observeAbsen()
    private val _cuti = cutiRepository.observeCuti()

    val notificationList = combine(
        _absen,
        _cuti,
    ) { absenList, cutiList ->
        NotificationModel.toNotification(absenList, cutiList)
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())
}