package com.example.klinikkasihibu.data.model

enum class AbsenType {
    Izin, Absen, TidakHadir;

    override fun toString(): String {
        return when (this) {
            Izin -> "Izin"
            Absen -> "Absen"
            TidakHadir -> "Tidak Hadir"
        }
    }

    companion object {
        fun convertFromString(type: String) : AbsenType {
            return when (type) {
                "Izin" -> Izin
                "Absen" -> Absen
                "Tidak Hadir" -> TidakHadir
                else -> Izin
            }
        }
    }
}