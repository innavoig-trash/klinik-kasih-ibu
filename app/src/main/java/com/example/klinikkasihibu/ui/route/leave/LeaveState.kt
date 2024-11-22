package com.example.klinikkasihibu.ui.route.leave

import android.net.Uri
import com.example.klinikkasihibu.util.DateSelection
import com.kizitonwose.calendar.core.CalendarDay
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.UUID
import javax.inject.Inject

data class LeaveState(
    val showBottomSheet: Boolean = false,
    val showUpload: Boolean = false,
    val showFilePicker: Boolean = false,
    val showSuccess: Boolean = false,
    val uuid: String = UUID.randomUUID().toString(),
    val date: DateSelection = DateSelection(),
    val category: String? = null,
    val file: Uri? = null,
    val description: String = "",
    val isLoading: Boolean = false
) {
    fun isValid(): Boolean {
        return date != DateSelection() &&
                file != null &&
                category != null
    }
}