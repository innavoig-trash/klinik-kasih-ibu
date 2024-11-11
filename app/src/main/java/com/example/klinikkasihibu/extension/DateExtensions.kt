package com.example.klinikkasihibu.extension

import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.ZoneId
import java.util.Date
import java.util.Locale

fun Date.formatDate(format: String, locale: Locale = Locale.forLanguageTag("id")): String {
    val formatter = SimpleDateFormat(format, locale)
    return formatter.format(this)
}

fun LocalDate.toDate(): Date {
    return Date.from(this.atStartOfDay(ZoneId.systemDefault()).toInstant())
}