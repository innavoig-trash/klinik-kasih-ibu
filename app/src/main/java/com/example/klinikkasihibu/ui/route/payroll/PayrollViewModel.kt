package com.example.klinikkasihibu.ui.route.payroll

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.klinikkasihibu.data.repository.PayrollRepository
import com.example.klinikkasihibu.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PayrollViewModel @Inject constructor(
    private val userRepository: UserRepository,
    payrollRepository: PayrollRepository,
): ViewModel() {
    private val _state = MutableStateFlow(PayrollState())
    val state = _state.asStateFlow()

    private val _payroll = payrollRepository.observePayroll()

    val monthList = _payroll
        .map { it.map { item -> item.month } }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())

    val selectedPayroll = combine(
        _state,
        _payroll,
    ) { state, payroll ->
        val selectedMonth = state.selectedMonth
        if (selectedMonth == null) {
            null
        } else {
            payroll.find { it.month == selectedMonth }
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), null)

    init {
        fetchUser()
    }

    private fun fetchUser() {
        viewModelScope.launch {
            val user = userRepository.fetchCurrentUser()
            _state.update { it.copy(user = user) }
        }
    }

    fun onMonthChange(month: String) {
        _state.update { old -> old.copy(selectedMonth = month) }
    }
}