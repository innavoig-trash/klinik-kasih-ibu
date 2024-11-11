package com.example.klinikkasihibu.ui.route.leave

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.klinikkasihibu.R
import com.example.klinikkasihibu.ui.component.PrimaryButton

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LeaveUploadDialog(
    onDismiss: () -> Unit,
    state: LeaveState,
    viewModel: LeaveViewModel
) {
    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(
            usePlatformDefaultWidth = false
        )
    ) {
        Scaffold(
            modifier = Modifier
                .fillMaxSize(),
            topBar = {
                TopAppBar(
                    title = {
                        Text("Upload")
                    },
                    navigationIcon = {
                        IconButton(
                            onClick = onDismiss
                        ) {
                            Icon(Icons.Default.ArrowBackIosNew, contentDescription = null)
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.background,
                        titleContentColor = MaterialTheme.colorScheme.onBackground
                    )
                )
            }
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background)
                    .padding(innerPadding)
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text("Upload Dokumen Cuti", style = MaterialTheme.typography.titleMedium)
                    Text("Tambahkan dokumen Anda di sini.", style = MaterialTheme.typography.bodyMedium)
                    if (state.file != null) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 16.dp)
                                .shadow(4.dp, RoundedCornerShape(8.dp))
                                .background(MaterialTheme.colorScheme.background)
                                .clickable {
                                    viewModel.onUploadFile()
                                }
                                .padding(32.dp),
                            horizontalArrangement = Arrangement.spacedBy(16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.folder),
                                contentDescription = "upload image",
                            )
                            Text(state.file.path ?: "", style = MaterialTheme.typography.titleMedium)
                        }
                    } else {
                        Image(
                            painter = painterResource(id = R.drawable.upload),
                            contentDescription = "upload image",
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 16.dp)
                                .clickable {
                                    viewModel.onUploadFile()
                                },
                            contentScale = ContentScale.Crop,
                        )
                    }
                }
                PrimaryButton(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = { viewModel.onUploadSave() }
                ) {
                    Text("Simpan")
                }
            }
        }
    }
}