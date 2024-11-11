package com.example.klinikkasihibu.ui.route.leave

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.klinikkasihibu.R
import com.example.klinikkasihibu.ui.component.PrimaryButton
import com.example.klinikkasihibu.ui.component.SuccessDialog

@Composable
fun LeaveSuccessDialog(
    onDismiss: () -> Unit
) {
    SuccessDialog(
        onDismiss = onDismiss,
        title = "Pengajuan Cuti Berhasil",
        description = "Permintaan cuti Anda sedang diproses oleh Admin. Harap bersabar karena mungkin memerlukan waktu. Terima kasih atas pengertian Anda.",
        buttonText = "Selesai"
    )
}

@Preview(
    showBackground = true,
    backgroundColor = 0xFFFFFFFF,
)
@Composable
fun LeaveSuccessDialogPreview() {
    MaterialTheme {
        LeaveSuccessDialog(
            onDismiss = {}
        )
    }
}