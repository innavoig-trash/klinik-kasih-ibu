package com.example.klinikkasihibu.ui.route.leave

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.klinikkasihibu.data.model.Cuti
import com.example.klinikkasihibu.data.model.CutiStatus
import com.example.klinikkasihibu.data.repository.CutiRepository
import com.example.klinikkasihibu.data.repository.UserRepository
import com.example.klinikkasihibu.extension.toDate
import com.example.klinikkasihibu.util.DateSelection
import com.google.firebase.auth.FirebaseAuth
import com.kizitonwose.calendar.core.CalendarDay
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
class LeaveViewModel @Inject constructor(
    private val cutiRepository: CutiRepository,
    private val userRepository: UserRepository,
): ViewModel() {
    private val _state = MutableStateFlow(LeaveState())
    val state = _state.asStateFlow()

    val cutiList = cutiRepository.observeCuti()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())

    fun onBottomSheetDismiss() {
        _state.update { old -> old.copy(showBottomSheet = false) }
    }

    fun onAddClick() {
        _state.update { old -> old.copy(showBottomSheet = true) }
    }

    fun onDateChange(date: DateSelection) {
        _state.update { old -> old.copy(date = date) }
    }

    fun onUploadClick() {
        _state.update { old -> old.copy(showUpload = true) }
    }

    fun onUploadDismiss() {
        _state.update { old -> old.copy(showUpload = false) }
    }

    fun onUploadFile() {
        _state.update { old -> old.copy(showFilePicker = true) }
    }

    fun onUploadSave() {
        _state.update { old -> old.copy(showUpload = false) }
    }

    fun onFilePicked(uri: Uri) {
        _state.update { old -> old.copy(file = uri) }
    }

    fun onFilePickerDone() {
        _state.update { old -> old.copy(showFilePicker = false) }
    }

    fun onCategoryChange(category: String) {
        _state.update { old -> old.copy(category = category) }
    }

    fun onSaveClick() {
        viewModelScope.launch {
            _state.update { old -> old.copy(isLoading = true) }
            val state = _state.value
            val user = userRepository.fetchCurrentUser() ?: return@launch
            val startDate = state.date.startDate?.toDate() ?: return@launch
            val endDate = state.date.endDate?.toDate() ?: return@launch
            val category = state.category ?: return@launch
            val file = state.file ?: return@launch
            val uri = cutiRepository.uploadCutiDocument(state.uuid, file)
            val cuti = Cuti(
                uuid = state.uuid,
                userId = user.id,
                username = user.name,
                role = user.role,
                startDate = startDate,
                endDate = endDate,
                category = category,
                status = CutiStatus.Menunggu,
                documentUrl = uri,
                description = state.description,
                createdAt = Date()
            )
            cutiRepository.upsertCuti(cuti)
            _state.update { old ->
                old.copy(
                    isLoading = false,
                    showBottomSheet = false,
                    showSuccess = true
                )
            }
        }
    }

    fun onDescriptionChange(description: String) {
        _state.update { old -> old.copy(description = description) }
    }

    fun onSuccessDismiss() {
        _state.update { old -> old.copy(showSuccess = false) }
    }
}