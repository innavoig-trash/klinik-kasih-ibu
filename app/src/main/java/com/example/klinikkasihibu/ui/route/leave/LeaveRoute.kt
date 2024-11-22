package com.example.klinikkasihibu.ui.route.leave

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.outlined.AddBox
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.outlined.Folder
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.klinikkasihibu.data.model.Cuti
import com.example.klinikkasihibu.data.model.cutiCategoryList
import com.example.klinikkasihibu.extension.formatDate
import com.example.klinikkasihibu.extension.toDate
import com.example.klinikkasihibu.ui.component.PrimaryButton
import com.example.klinikkasihibu.ui.component.SimpleCalendarTitle
import com.example.klinikkasihibu.ui.component.topAppBarColors
import com.example.klinikkasihibu.util.ContinuousSelectionHelper.getSelection
import com.example.klinikkasihibu.util.DateSelection
import com.example.klinikkasihibu.util.backgroundHighlight
import com.kizitonwose.calendar.compose.HorizontalCalendar
import com.kizitonwose.calendar.compose.rememberCalendarState
import com.kizitonwose.calendar.core.CalendarDay
import com.kizitonwose.calendar.core.DayPosition
import com.kizitonwose.calendar.core.daysOfWeek
import com.kizitonwose.calendar.core.nextMonth
import com.kizitonwose.calendar.core.previousMonth
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.YearMonth

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LeaveRoute(
    onBackStack: () -> Unit,
    viewModel: LeaveViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val cutiList by viewModel.cutiList.collectAsStateWithLifecycle()
    val currentMonth = remember { YearMonth.now() }
    val startMonth = remember { currentMonth.minusMonths(100) }
    val endMonth = remember { currentMonth.plusMonths(100) }
    val daysOfWeek = remember { daysOfWeek() }
    val today = remember { LocalDate.now() }
    val isKeyboardOpen by keyboardAsState()

    val calendarState = rememberCalendarState(
        startMonth = startMonth,
        endMonth = endMonth,
        firstVisibleMonth = currentMonth,
        firstDayOfWeek = daysOfWeek.first(),
    )
    val coroutineScope = rememberCoroutineScope()

    val filePickerLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.OpenDocument()
    ) { uri ->
        uri?.let { viewModel.onFilePicked(it) }
    }

    LaunchedEffect(state.showFilePicker) {
        if (state.showFilePicker) {
            filePickerLauncher.launch(arrayOf("application/pdf"))
            viewModel.onFilePickerDone()
        }
    }

    if (state.showUpload) {
        LeaveUploadDialog(
            onDismiss = { viewModel.onUploadDismiss() },
            state = state,
            viewModel = viewModel
        )
    }

    if (state.showSuccess) {
        LeaveSuccessDialog(
            onDismiss = { viewModel.onSuccessDismiss() }
        )
    }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.surface,
        topBar = {
            CenterAlignedTopAppBar(
                modifier = Modifier.shadow(4.dp),
                title = {
                    Text("Pengajuan Cuti")
                },
                navigationIcon = {
                    IconButton(
                        onClick = onBackStack
                    ) {
                        Icon(Icons.Default.ArrowBackIosNew, contentDescription = null)
                    }
                },
                actions = {
                    IconButton(
                        onClick = viewModel::onAddClick
                    ) {
                        Icon(Icons.Outlined.AddBox, contentDescription = null)
                    }
                },
                colors = topAppBarColors
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .padding(innerPadding)
                .background(MaterialTheme.colorScheme.surface)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(bottom = 16.dp)
        ) {
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    PrimaryButton(
                        modifier = Modifier.weight(1f),
                        onClick = {

                        }
                    ) {
                        Text("Upcoming")
                    }
                    PrimaryButton(
                        modifier = Modifier
                            .weight(1f),
                        onClick = {},
                    ) {
                        Text("Past")
                    }
                }
            }
            items(items = cutiList, key = { it.uuid }) { cuti ->
                CutiCard(cuti = cuti)
            }
        }

        if (state.showBottomSheet) {
            ModalBottomSheet(
                onDismissRequest = viewModel::onBottomSheetDismiss,
                containerColor = Color.White,
                sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("Tetapkan Tanggal Cuti", style = MaterialTheme.typography.titleMedium)
                        IconButton(
                            onClick = viewModel::onBottomSheetDismiss
                        ) {
                            Icon(Icons.Outlined.Close, contentDescription = null)
                        }
                    }
                    if (!isKeyboardOpen) {
                        HorizontalCalendar(
                            state = calendarState,
                            dayContent = { day ->
                                Day(
                                    day = day,
                                    today = LocalDate.now(),
                                    selection = state.date,
                                ) { value ->
                                    if (value.position == DayPosition.MonthDate &&
                                        (value.date == today || value.date.isAfter(today))
                                    ) {
                                        val selection = getSelection(
                                            clickedDate = value.date,
                                            dateSelection = state.date,
                                        )
                                        viewModel.onDateChange(selection)
                                    }
                                }
                            },
                        )
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                    ) {
                        Column(
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Text("Mulai", style = MaterialTheme.typography.bodyMedium)
                            Text(
                                text = state.date.startDate?.toDate()?.formatDate("dd MMM yyyy") ?: "Pilih Tanggal",
                                style = MaterialTheme.typography.titleMedium,
                                color = if (state.date.startDate != null) MaterialTheme.colorScheme.onBackground else MaterialTheme.colorScheme.error
                            )
                        }
                        Column(
                            horizontalAlignment = Alignment.End,
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Text("Sampai", style = MaterialTheme.typography.bodyMedium)
                            Text(
                                text = state.date.endDate?.toDate()?.formatDate("dd MMM yyyy") ?: "Pilih Tanggal",
                                style = MaterialTheme.typography.titleMedium,
                                color = if (state.date.endDate != null) MaterialTheme.colorScheme.onBackground else MaterialTheme.colorScheme.error
                            )
                        }
                    }
                    Text(
                        modifier = Modifier
                            .padding(top = 16.dp, bottom = 4.dp),
                        text = "Kategori Cuti",
                        style = MaterialTheme.typography.titleMedium,
                    )
                    var showDropdown by remember { mutableStateOf(false) }
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.CenterEnd,
                    ){
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(8.dp))
                                .clickable { showDropdown = !showDropdown }
                                .background(MaterialTheme.colorScheme.surface)
                                .padding(16.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            val text = if (state.category != null) "${state.category} " else "Pilihan kategori cuti"
                            Text(text, color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f))
                            Icon(Icons.Default.KeyboardArrowDown, contentDescription = null, tint = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f))
                        }
                        DropdownMenu(
                            expanded = showDropdown,
                            onDismissRequest = { showDropdown = false },
                        ) {
                            cutiCategoryList.forEach {
                                DropdownMenuItem(
                                    onClick = {
                                        viewModel.onCategoryChange(it)
                                        showDropdown = false
                                    },
                                    text = { Text(it) },
                                )
                            }
                        }
                    }
                    Column(
                        modifier = Modifier.padding(top = 16.dp),
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Text("Keterangan mengajukan cuti", style = MaterialTheme.typography.titleMedium)
                        OutlinedTextField(
                            modifier = Modifier
                                .fillMaxWidth(),
                            value = state.description,
                            onValueChange = { viewModel.onDescriptionChange(it) },
                            placeholder = { Text(text = "Masukkan Keterangan") },
                        )
                    }
                    OutlinedButton(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 16.dp),
                        onClick = { viewModel.onUploadClick() },
                        shape = RoundedCornerShape(4.dp),
                        border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary)
                    ) {
                        val icon = if (state.file != null) Icons.Default.Check else Icons.Outlined.Folder
                        Icon(icon, contentDescription = null)
                        Spacer(modifier = Modifier.width(8.dp))
                        val text = if (state.file != null) "Dokumen Tersimpan" else "Upload Dokumen Cuti"
                        Text(text)
                    }
                    PrimaryButton(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 16.dp),
                        enabled = state.isValid() && !state.isLoading,
                        onClick = { viewModel.onSaveClick() }
                    ) {
                        Text("Konfirmasi Cuti")
                    }
                }
            }
        }
    }
}


@Composable
fun CutiCard(
    cuti: Cuti,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.background, RoundedCornerShape(8.dp))
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Column {
            Text("Tanggal")
            val startDate = cuti.startDate.formatDate("dd MMM yyyy")
            val endDate = cuti.endDate.formatDate("dd MMM yyyy")
            Text("$startDate - $endDate", style = MaterialTheme.typography.titleMedium)
            Text("Jumlah Hari")
            Text("${cuti.duration} Hari", style = MaterialTheme.typography.titleMedium)
        }
        Text(cuti.status.toString())
    }
}

@Composable
private fun Day(
    day: CalendarDay,
    today: LocalDate,
    selection: DateSelection,
    onClick: (CalendarDay) -> Unit,
) {
    var textColor = Color.Transparent
    Box(
        modifier = Modifier
            .aspectRatio(1f) // This is important for square-sizing!
            .clickable(
                enabled = day.position == DayPosition.MonthDate && day.date >= today,
                indication = null,
                interactionSource = remember { MutableInteractionSource() },
                onClick = { onClick(day) },
            )
            .backgroundHighlight(
                day = day,
                today = today,
                selection = selection,
                selectionColor = MaterialTheme.colorScheme.primary,
                continuousSelectionColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.3f),
            ) { textColor = it },
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = day.date.dayOfMonth.toString(),
            color = textColor,
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
        )
    }
}

@Composable
fun keyboardAsState(): State<Boolean> {
    val isImeVisible = WindowInsets.ime.getBottom(LocalDensity.current) > 0
    return rememberUpdatedState(isImeVisible)
}