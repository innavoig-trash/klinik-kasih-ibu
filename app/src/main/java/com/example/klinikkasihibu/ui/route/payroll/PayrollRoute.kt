package com.example.klinikkasihibu.ui.route.payroll

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.example.klinikkasihibu.R
import com.example.klinikkasihibu.ui.component.PrimaryButton
import com.example.klinikkasihibu.ui.component.topAppBarColors
import com.example.klinikkasihibu.util.toCurrency

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PayrollRoute(
    onBackStack: () -> Unit,
    viewModel: PayrollViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val user by viewModel.user.collectAsStateWithLifecycle()
    val monthList by viewModel.monthList.collectAsStateWithLifecycle()
    val selectedPayroll by viewModel.selectedPayroll.collectAsStateWithLifecycle()

    Log.d("TAG", monthList.toString())

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                colors = topAppBarColors.copy(containerColor = MaterialTheme.colorScheme.background),
                title = {
                    Text("Slip Gaji Karyawan")
                },
                navigationIcon = {
                    IconButton(
                        onClick = onBackStack
                    ) {
                        Icon(Icons.Default.ArrowBackIosNew, contentDescription = null)
                    }
                }
            )
        },
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding),
        ) {
            if (user != null) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                        .background(MaterialTheme.colorScheme.surface, RoundedCornerShape(16.dp))
                        .padding(24.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(user!!.name, style = MaterialTheme.typography.titleLarge)
                        Text(user!!.role)
                    }
                    if (user!!.imageUrl != null) {
                        AsyncImage(
                            modifier = Modifier
                                .size(48.dp)
                                .clip(CircleShape),
                            model = user!!.imageUrl,
                            contentDescription = null
                        )
                    } else {
                        Image(
                            modifier = Modifier
                                .size(48.dp)
                                .clip(CircleShape),
                            painter = painterResource(R.drawable.baseline_person_24),
                            contentDescription = null
                        )
                    }
                }
            }
            var expanded by remember { mutableStateOf(false) }
            Box(
                contentAlignment = Alignment.Center
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .background(MaterialTheme.colorScheme.surface)
                        .clickable {
                            expanded = true
                        }
                        .padding(24.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(state.selectedMonth ?: "Pilih bulan")
                    Icon(Icons.Default.KeyboardArrowDown, contentDescription = null)
                }
                DropdownMenu(
                    modifier = Modifier
                        .fillMaxWidth(),
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    monthList.forEach { month ->
                        DropdownMenuItem(
                            modifier = Modifier.fillMaxWidth(),
                            onClick = {
                                viewModel.onMonthChange(month)
                                expanded = false
                            },
                            text = { Text(month) },
                        )
                    }
                }
            }
            HorizontalDivider()
            if (state.selectedMonth != null) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.surface)
                        .verticalScroll(rememberScrollState()),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Spacer(modifier = Modifier.height(32.dp))
                    Row {
                        Text(
                            "Total Gaji",
                            color = MaterialTheme.colorScheme.primary,
                            style = MaterialTheme.typography.titleMedium,
                            textAlign = TextAlign.Center,
                        )
                        Text(
                            text = when (selectedPayroll?.taken) {
                                true -> " (Sudah Diambil)"
                                false -> " (Belum Diambil)"
                                null -> ""
                            },
                            color = when (selectedPayroll?.taken) {
                                true -> Color.Green
                                false -> Color.Red
                                null -> MaterialTheme.colorScheme.onSurface
                            },
                            style = MaterialTheme.typography.titleMedium,
                            textAlign = TextAlign.Center,
                        )
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        selectedPayroll?.total?.toCurrency() ?: "",
                        style = MaterialTheme.typography.headlineLarge
                            .copy(fontWeight = FontWeight.Bold),
                    )
                    Spacer(modifier = Modifier.height(32.dp))
                    Column(
                        modifier = Modifier
                            .padding(horizontal = 16.dp)
                            .fillMaxWidth()
                            .background(
                                MaterialTheme.colorScheme.background,
                                RoundedCornerShape(8.dp)
                            )
                            .padding(20.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                    ) {
                        Text(
                            "Keterangan:",
                            color = MaterialTheme.colorScheme.primary,
                            style = MaterialTheme.typography.titleMedium
                        )
                        HorizontalDivider()
                        Row(
                            modifier = Modifier
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                        ) {
                            Text("Gaji Pokok")
                            Text(selectedPayroll?.base?.toCurrency() ?: "")
                        }
                        selectedPayroll?.additional?.forEach { item ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                            ) {
                                Text(item.name)
                                Text(item.amount.toCurrency())
                            }
                        }
                    }
//                    Spacer(modifier = Modifier.height(32.dp))
//                    Column(
//                        modifier = Modifier
//                            .padding(horizontal = 16.dp)
//                            .fillMaxWidth()
//                            .background(
//                                MaterialTheme.colorScheme.background,
//                                RoundedCornerShape(8.dp)
//                            )
//                            .padding(20.dp),
//                        verticalArrangement = Arrangement.spacedBy(8.dp),
//                    ) {
//                        Text(
//                            "Ringkasan Kehadiran:",
//                            color = MaterialTheme.colorScheme.primary,
//                            style = MaterialTheme.typography.titleMedium
//                        )
//                        HorizontalDivider()
//                        Row(
//                            modifier = Modifier
//                                .fillMaxWidth(),
//                            horizontalArrangement = Arrangement.SpaceBetween,
//                        ) {
//                            Text("Hari Kerja Aktual")
//                            Text("${selectedPayroll?.actualDay ?: 0} hari")
//                        }
//                        Row(
//                            modifier = Modifier
//                                .fillMaxWidth(),
//                            horizontalArrangement = Arrangement.SpaceBetween,
//                        ) {
//                            Text("Hari Bekerja")
//                            Text("${selectedPayroll?.workDay ?: 0} hari")
//                        }
//                        Row(
//                            modifier = Modifier
//                                .fillMaxWidth(),
//                            horizontalArrangement = Arrangement.SpaceBetween,
//                        ) {
//                            Text("Cuti Kerja")
//                            Text("${selectedPayroll?.leaveDay ?: 0} hari")
//                        }
//                        Row(
//                            modifier = Modifier
//                                .fillMaxWidth(),
//                            horizontalArrangement = Arrangement.SpaceBetween,
//                        ) {
//                            Text("Kehadiran")
//                            Text("${selectedPayroll?.presentDay ?: 0} hari")
//                        }
                    }
//                    Box(
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .background(MaterialTheme.colorScheme.surface),
//                    ) {
//                        PrimaryButton(
//                            modifier = Modifier
//                                .fillMaxWidth()
//                                .padding(16.dp),
//                            onClick = { }
//                        ) {
//                            Row(
//                                verticalAlignment = Alignment.CenterVertically,
//                                horizontalArrangement = Arrangement.SpaceBetween
//                            ) {
//                                Icon(Icons.Default.Download, contentDescription = null)
//                                Spacer(modifier = Modifier.width(8.dp))
//                                Text("Download Slip Gaji")
//                            }
//                        }
//                    }
//                }
            } else {
                Box(
                    Modifier
                        .fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        modifier = Modifier
                            .size(200.dp),
                        painter = painterResource(R.drawable.choose_month),
                        contentDescription = null,
                    )
                }
            }
        }
    }
}