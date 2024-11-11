package com.example.klinikkasihibu.ui.route.main.home

import androidx.compose.runtime.Composable
import com.example.klinikkasihibu.ui.component.SuccessDialog

@Composable
fun AbsenSuccessDialog(
    onDismiss: () -> Unit
) {
    SuccessDialog(
        onDismiss = onDismiss,
        title = "Kehadiran Berhasil",
        description = "Kerja bagus! Kehadiran Anda telah berhasil dicatat. Anda sudah siap bekerja untuk hari ini.",
        buttonText = "Done"
    )
}