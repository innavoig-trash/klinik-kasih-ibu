package com.example.klinikkasihibu.ui.route.payroll

import com.example.klinikkasihibu.data.model.User

data class PayrollState(
    val user: User? = null,
    val selectedMonth: String? = null,
)