package com.example.klinikkasihibu.ui.route.main.home

import android.Manifest
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.example.klinikkasihibu.R
import com.example.klinikkasihibu.data.model.Absen
import com.example.klinikkasihibu.data.model.AbsenType
import com.example.klinikkasihibu.extension.formatDate
import java.util.Calendar
import java.util.Date

@Composable
fun HomeRoute(
    toPayroll: () -> Unit,
    toNotification: () -> Unit,
    toLeave: () -> Unit,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val absen by viewModel.absen.collectAsStateWithLifecycle()
    val absenDialogState by viewModel.absenDialogState.collectAsStateWithLifecycle()
    val successDialogState by viewModel.sucessDialogState.collectAsStateWithLifecycle()
    val permissionRequest by viewModel.permissionRequest.collectAsStateWithLifecycle()

    val currentDate = Date()
    val hour = currentDate.formatDate("HH:mm")
    val date = currentDate.formatDate("EEEE, dd MMMM yyyy")
    val brush = Brush.verticalGradient(listOf(MaterialTheme.colorScheme.primary, Color.White))

    val locationPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGranted ->
            viewModel.onPermissionResult(
                isGranted = isGranted
            )
        }
    )

    LaunchedEffect(state.shouldNavigateToLeave) {
        if (state.shouldNavigateToLeave) {
            toLeave()
            viewModel.onNavigationDone()
        }
    }

    permissionRequest?.let { request ->
        LaunchedEffect(request) {
            when (request) {
                Manifest.permission.ACCESS_FINE_LOCATION -> {
                    locationPermissionLauncher.launch(
                        Manifest.permission.ACCESS_FINE_LOCATION
                    )
                }
            }
            viewModel.onPermissionRequestDone()
        }
    }

    absenDialogState?.let { state ->
        AbsenDialog(
            isSuccess = state,
            onDismiss = { viewModel.onAbsenDialogDismiss() },
            onConfirm = { viewModel.onAbsenDialogConfirm() }
        )
    }

    successDialogState?.let { state ->
        if (state) {
            AbsenSuccessDialog(
                onDismiss = { viewModel.onSuccessDialogDismiss() }
            )
        }
    }

    Box(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.secondary)
            .fillMaxSize()
    ) {
        Image(
            modifier = Modifier.height(300.dp),
            painter = painterResource(R.drawable.vector),
            contentScale = ContentScale.FillHeight,
            contentDescription = null
        )
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .padding(top = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        AsyncImage(
                            modifier = Modifier
                                .size(48.dp)
                                .clip(CircleShape)
                                .background(MaterialTheme.colorScheme.surface),
                            model = state.user?.imageUrl,
                            contentDescription = null
                        )
                        Column {
                            Text(state.user?.name ?: "Nama Karyawan", color = MaterialTheme.colorScheme.onPrimary)
                            Text(state.user?.role ?: "Perawat", color = MaterialTheme.colorScheme.onPrimary)
                        }
                    }
                    IconButton(
                        onClick = toNotification
                    ) {
                        Icon(Icons.Outlined.Notifications, contentDescription = null, tint = MaterialTheme.colorScheme.onPrimary)
                    }
                }
            }
            item {
                Text(
                    modifier = Modifier.padding(top = 32.dp),
                    text = hour,
                    color = MaterialTheme.colorScheme.onPrimary,
                    style = MaterialTheme.typography.headlineLarge
                )
            }
            item {
                Text(date, color = MaterialTheme.colorScheme.onPrimary)
            }
            item {
                Column(
                    modifier = Modifier
                        .padding(top = 32.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .background(brush = brush)
                        .size(128.dp)
                        .clickable {
                            if (!state.isHadirLoading && !state.isAlreadyPresent) {
                                viewModel.onPresentClick()
                            }
                        },
                    verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    if (state.isHadirLoading) {
                        CircularProgressIndicator(
                            color = Color.Black
                        )
                    } else {
                        val drawable = when (state.isAlreadyPresent) {
                            true -> R.drawable.baseline_done_24
                            false -> R.drawable.click
                        }
                        Icon(
                            modifier = Modifier
                                .size(48.dp),
                            painter = painterResource(drawable),
                            contentDescription = null
                        )
                        Text("Hadir", style = MaterialTheme.typography.bodyMedium)
                    }
                }
            }
            item {
               Row(
                   modifier = Modifier
                       .padding(top = 32.dp),
                   horizontalArrangement = Arrangement.spacedBy(8.dp)
               ) {
                   Icon(Icons.Filled.LocationOn, contentDescription = null, tint = MaterialTheme.colorScheme.onPrimary)
                   Text("Jl. Hajimena No.22, Natar", color = MaterialTheme.colorScheme.onPrimary)
               }
            }
            item {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 56.dp)
                        .background(
                            MaterialTheme.colorScheme.surface,
                            RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
                        )
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(110.dp),
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        HomeMenuItem.items.forEach { item ->
                            HomeMenuButton(
                                modifier = Modifier
                                    .weight(1f)
                                    .fillMaxHeight(),
                                image = item.image,
                                text = item.text,
                                onClick = {
                                    when (item.text) {
                                        "Izin/Cuti" -> toLeave()
                                        "Payroll" -> toPayroll()
                                        "Riwayat Presensi" -> {}
                                    }
                                }
                            )
                        }
                    }
                    Text("Activity", style = MaterialTheme.typography.titleMedium)
                }
            }
            if (absen.isNotEmpty()) {
                items(items = absen, key = { it.uuid }) { item ->
                    ActivityCard(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(MaterialTheme.colorScheme.surface)
                            .padding(horizontal = 16.dp)
                            .padding(bottom = 16.dp),
                        absen = item
                    )
                }
            } else {
                item {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(MaterialTheme.colorScheme.surface),
                        text = "Belum ada absensi",
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }
            item {
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(128.dp)
                        .background(MaterialTheme.colorScheme.surface)
                )
            }
        }
    }
}

data class HomeMenuItem(
    val image: Int,
    val text: String,
) {
    companion object {
        val items = listOf(
            HomeMenuItem(R.drawable.izin, "Izin/Cuti"),
            HomeMenuItem(R.drawable.payroll, "Payroll"),
            HomeMenuItem(R.drawable.arsip, "Riwayat Presensi"),
        )
    }
}

@Composable
fun HomeMenuButton(
    modifier: Modifier = Modifier,
    image: Int,
    text: String,
    onClick: () -> Unit
) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(16.dp))
            .background(Color.White)
            .clickable { onClick() }
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(painter = painterResource(image), contentDescription = null)
        Text(text, style = MaterialTheme.typography.bodySmall, textAlign = TextAlign.Center)
    }
}

@Composable
fun ActivityCard(
    modifier: Modifier = Modifier,
    absen: Absen
) {
    Row(
        modifier = modifier
            .background(Color.White, RoundedCornerShape(16.dp))
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Image(
                painter = painterResource(
                    when (absen.type) {
                        AbsenType.Izin -> R.drawable.activity_izin
                        AbsenType.Absen -> R.drawable.activity_hadir
                        AbsenType.TidakHadir -> R.drawable.activity_izin
                    }
                ),
                contentDescription = null,
                modifier = Modifier.size(32.dp)
            )
            Column(
                modifier = Modifier
                    .padding(start = 16.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(absen.type.toString(), style = MaterialTheme.typography.titleMedium)
                Text(absen.date.formatDate("dd MMMM yyyy"), style = MaterialTheme.typography.bodyMedium)
            }
        }
        when (absen.type) {
            AbsenType.Izin -> {
                Text("Izin", style = MaterialTheme.typography.bodyMedium)
            }
            AbsenType.Absen -> {
                Column(
                    verticalArrangement = Arrangement.spacedBy(4.dp),
                    horizontalAlignment = Alignment.End
                ) {
                    val text = when (isLaterThan9am(absen.date)) {
                        true -> "Late"
                        false -> "On Time"
                    }
                    Text(absen.date.formatDate("HH:mm"), style = MaterialTheme.typography.titleMedium)
                    Text(text, style = MaterialTheme.typography.bodyMedium)
                }
            }

            AbsenType.TidakHadir -> {}
        }
    }
}

fun isLaterThan9am(date: Date): Boolean {
    val calendar = Calendar.getInstance()
    calendar.time = date
    return calendar.get(Calendar.HOUR_OF_DAY) >= 9
}