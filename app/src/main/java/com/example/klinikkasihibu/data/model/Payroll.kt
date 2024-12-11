package com.example.klinikkasihibu.data.model

import java.util.Date

data class Payroll(
    val id: String,
    val userId: String,
    val month: String,
//    val actualDay: Int,
//    val leaveDay: Int,
//    val presentDay: Int,
//    val workDay: Int,
    val taken: Boolean,
    val total: Double,
    val additional: List<PayrollAdditional>,
    val base: Double,
//    val document: String,
    val createdAt: Date,
) {

}

data class PayrollAdditional(
    val name: String,
    val amount: Double,
)