package com.example.klinikkasihibu.ui.route.main.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.klinikkasihibu.R
import com.example.klinikkasihibu.ui.component.PrimaryButton

@Composable
fun AbsenDialog(
    isSuccess: Boolean = false,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit
) {
    val title = when (isSuccess) {
        true -> "Anda berada di area kehadiran"
        false -> "Anda berada di luar area kehadiran"
    }

    val description = when (isSuccess) {
        true -> "Anda berada di area Kehadiran. Untuk melanjutkan, lakukan Presensi."
        false -> "Maaf, sepertinya Anda berada di luar area Kehadiran. Untuk melanjutkan, lakukan dengan izin."
    }

    val buttonText = when (isSuccess) {
        true -> "Presensi"
        false -> "Membuat Izin"
    }

    Dialog(
        onDismissRequest = onDismiss
    ) {
        Column(
            modifier = Modifier
                .background(Color.White, RoundedCornerShape(16.dp))
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                IconButton(
                    onClick = onDismiss
                ) {
                    Icon(Icons.Default.Close, contentDescription = null)
                }
            }
            Image(
                painter = painterResource(R.drawable.absen),
                contentDescription = "Absen"
            )
            Text(text = title, style = MaterialTheme.typography.titleMedium)
            Text(text = description, textAlign = TextAlign.Center, style = MaterialTheme.typography.bodyMedium)
            PrimaryButton(
                onClick = onConfirm,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = buttonText)
            }
        }
    }
}