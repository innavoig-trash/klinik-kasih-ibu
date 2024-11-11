package com.example.klinikkasihibu.ui.component

import android.app.AlertDialog
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.klinikkasihibu.R

@Composable
fun LocationPermissionDialog(
    isPermanentlyDeclined: Boolean,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
    onGoToAppSetting: () -> Unit,
    modifier: Modifier = Modifier
) {
    Dialog(
        onDismissRequest = onDismiss,
    ) {
        Column(
            modifier = modifier
                .background(Color.White, RoundedCornerShape(16.dp))
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row {
                IconButton(
                    onClick = onDismiss
                ) {
                    Icon(Icons.Outlined.Close, contentDescription = null)
                }
            }
            Image(
                painter = painterResource(R.drawable.location),
                contentDescription = null
            )
            HorizontalDivider()
            Text("Aktifkan Lokasi untuk Kehadiran", style = MaterialTheme.typography.titleMedium, textAlign = TextAlign.Center)
            if (isPermanentlyDeclined) {
                PrimaryButton(
                    onClick = onGoToAppSetting
                ) {
                    Text("Go to App Settings")
                }
            } else {
                PrimaryButton(
                    onClick = onConfirm
                ) {
                    Text("Turn On Location")
                }
            }
        }
    }
}