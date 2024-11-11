package com.example.klinikkasihibu.ui.route.leave

import android.net.Uri
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
    val date: Pair<CalendarDay?, CalendarDay?> = Pair(null, null),
    val category: String? = null,
    val file: Uri? = null,
    val isLoading: Boolean = false
) {
    fun isValid(): Boolean {
        return date.first != null &&
                date.second != null &&
                file != null &&
                category != null
    }
}