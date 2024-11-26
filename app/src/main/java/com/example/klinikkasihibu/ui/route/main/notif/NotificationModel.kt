package com.example.klinikkasihibu.ui.route.main.notif

import com.example.klinikkasihibu.data.model.Absen
import com.example.klinikkasihibu.data.model.AbsenType
import com.example.klinikkasihibu.data.model.Cuti
import com.example.klinikkasihibu.data.model.CutiStatus
import com.example.klinikkasihibu.extension.formatDate
import java.util.Date

data class NotificationModel(
    val message: String,
    val imageUrl: String?,
    val date: Date,
) {
    companion object {
        private fun fromAbsen(absen: Absen): NotificationModel {
            val text = when (absen.type) {
                AbsenType.Izin -> "${absen.username} izin ${absen.date.formatDate("dd MMMM yyyy")}"
                AbsenType.Absen -> "${absen.username} hadir ${absen.date.formatDate("dd MMMM yyyy")}"
                AbsenType.TidakHadir -> "${absen.username} tidak hadir ${absen.date.formatDate("dd MMMM yyyy")}"
            }
            return NotificationModel(
                message = text,
                imageUrl = absen.userImageUrl,
                date = absen.date
            )
        }

        private fun fromCuti(cuti: Cuti): List<NotificationModel> {
            val list = mutableListOf(
                NotificationModel(
                    message = "${cuti.username} mengajukan cuti ${cuti.createdAt.formatDate("dd MMMM yyyy")}",
                    imageUrl = cuti.userImageUrl,
                    date = cuti.createdAt
                )
            )
            if (cuti.updatedAt != null) {
                val text = when (cuti.status) {
                    CutiStatus.Menunggu -> ""
                    CutiStatus.Diterima -> "pengajuan cuti di terima"
                    CutiStatus.Ditolak -> "pengajuan cuti di tolak"
                }
                list.add(
                    NotificationModel(
                        message = "${cuti.username} $text ${cuti.updatedAt.formatDate("dd MMMM yyyy")}",
                        imageUrl = cuti.userImageUrl,
                        date = cuti.updatedAt
                    )
                )
            }
            return list
        }

        fun toNotification(absenList: List<Absen>, cutiList: List<Cuti>): List<NotificationModel> {
            val list = mutableListOf<NotificationModel>()
            absenList.forEach { absen ->
                list.add(fromAbsen(absen))
            }
            cutiList.forEach { cuti ->
                list.addAll(fromCuti(cuti))
            }
            return list.sortedByDescending { it.date }
        }
    }
}