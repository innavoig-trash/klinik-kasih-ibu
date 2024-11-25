package com.example.klinikkasihibu.data.model.dto

import com.example.klinikkasihibu.data.model.Payroll
import com.example.klinikkasihibu.data.model.PayrollAdditional
import java.util.Date

data class PayrollDto(
    val id: String? = null,
    val userId: String? = null,
    val actualDay: Int? = null,
    val leaveDay: Int? = null,
    val presentDay: Int? = null,
    val workDay: Int? = null,
    val total: Double? = null,
    val additional: List<PayrollAdditionalDto>? = null,
    val base: Double? = null,
    val document: String? = null,
    val month: String? = null,
    val createdAt: Date? = null,
) {
    fun toDomain(): Payroll {
        return Payroll(
            id = id ?: "",
            userId = userId ?: "",
            actualDay = actualDay ?: 0,
            leaveDay = leaveDay ?: 0,
            presentDay = presentDay ?: 0,
            workDay = workDay ?: 0,
            total = total ?: 0.0,
            additional = additional?.map { it.toDomain() } ?: emptyList(),
            base = base ?: 0.0,
            month = month ?: "",
            createdAt = createdAt ?: Date(),
            document = document ?: "",
        )
    }
}

data class PayrollAdditionalDto(
    val name: String? = null,
    val amount: Double? = null,
) {
    fun toDomain(): PayrollAdditional {
        return PayrollAdditional(
            name = name ?: "",
            amount = amount ?: 0.0,
        )
    }
}