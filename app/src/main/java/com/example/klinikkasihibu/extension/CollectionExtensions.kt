package com.example.klinikkasihibu.extension

fun <K, V> Map<K, V>.prepend(key: K, value: V): Map<K, V> {
    return mapOf(key to value) + this
}