package com.example.klinikkasihibu.ui.route.main.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.klinikkasihibu.data.model.Absen
import com.example.klinikkasihibu.data.model.AbsenEntry
import com.example.klinikkasihibu.data.model.AbsenType
import com.example.klinikkasihibu.data.repository.AbsenRepository
import com.example.klinikkasihibu.data.repository.UserRepository
import com.example.klinikkasihibu.extension.formatDate
import com.example.klinikkasihibu.extension.prepend
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val absenRepository: AbsenRepository
): ViewModel() {
    private val _state = MutableStateFlow(HomeState())
    val state = _state.asStateFlow()

    private val _absenDialogState = MutableStateFlow<Boolean?>(null)
    val absenDialogState = _absenDialogState.asStateFlow()

    private val _sucessDialogState = MutableStateFlow<Boolean?>(null)
    val sucessDialogState = _sucessDialogState.asStateFlow()

    private val _absen = absenRepository.observeAbsen()
    val absen = _absen
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5_000),
            emptyList()
        )

    private val _permissionRequest = MutableStateFlow<String?>(null)
    val permissionRequest = _permissionRequest.asStateFlow()

    init {
        _permissionRequest.update { android.Manifest.permission.ACCESS_FINE_LOCATION }
        fetchUser()
    }

    private fun fetchUser() {
        viewModelScope.launch {
            val user = userRepository.fetchCurrentUser()
            _state.update { it.copy(user = user) }
        }
    }

    fun onPresentClick() {
        if (!_state.value.locationPermissionGranted) {
            _permissionRequest.update { android.Manifest.permission.ACCESS_FINE_LOCATION }
        } else {
            _absenDialogState.update { true }
        }
    }

    private fun insertAbsen() {
        _state.update { old -> old.copy(isHadirLoading = true) }
        viewModelScope.launch {
            try {
                val date = Date()
                val dateString = date.formatDate("yyyy-MM-dd")
                val user = _state.value.user ?: throw Exception("User not logged in")
                val newAbsen = Absen(
                    uuid = "${dateString}-${user.id}",
                    userId = user.id,
                    dateString = dateString,
                    type = AbsenType.Absen,
                    date = date
                )
                absenRepository.upsertAbsen(newAbsen)
                _state.update { old -> old.copy(isHadirLoading = false) }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun onPermissionResult(
        isGranted: Boolean
    ) {
        _state.update { old -> old.copy(locationPermissionGranted = isGranted) }
    }

    fun onPermissionRequestDone() {
        _permissionRequest.update { null }
    }

    fun onAbsenDialogConfirm() {
        insertAbsen()
        _absenDialogState.update { null }
        _sucessDialogState.update { true }
    }

    fun onSuccessDialogDismiss() {
        _sucessDialogState.update { null }
    }

    fun onAbsenDialogDismiss() {
        _absenDialogState.update { null }
    }

    private fun addEntryToAbsenEntries(
        absenEntry: AbsenEntry,
        entries: Map<String, AbsenEntry>
    ): Map<String, AbsenEntry> {
        return entries.prepend(absenEntry.date.formatDate("yyyy-MM-dd"), absenEntry)
    }
}