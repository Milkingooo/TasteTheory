package com.example.coffeevibe.utils

import java.sql.Date
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.Locale

object TimeUtils {
    fun convertToMills(pickupTime: String) : Long {
        val parts = pickupTime.split("=") // Разбиваем строку на части
        val seconds = parts[1].split(",")[0].toLong() // Извлекаем секунды
        val nanoseconds = parts[2].split(")")[0].toLong() // Извлекаем наносекунды
        return seconds * 1000 + nanoseconds / 1_000_000
    }

    fun convertToDateWithFormat(pickupTime: String, format: String? = null) : String {
        val parts = pickupTime.split("=") // Разбиваем строку на части
        val seconds = parts[1].split(",")[0].toLong() // Извлекаем секунды
        val nanoseconds = parts[2].split(")")[0].toLong() // Извлекаем наносекунды
        val mills =  seconds * 1000 + nanoseconds / 1_000_000

        val date = Date(mills)
        val formatOfDate = SimpleDateFormat(format)
        return formatOfDate.format(date)
    }

    fun convertTimestampToLocalDateTime(timestamp: String): LocalDateTime? {
        return Instant
            .ofEpochMilli(convertToMills(timestamp))
            .atZone(ZoneId.systemDefault())
            .toLocalDateTime()
    }

}