package com.colegiosociologosperu.cspmovillimacallao.presentation.utils.time

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import android.os.Build
import androidx.annotation.RequiresApi

@RequiresApi(Build.VERSION_CODES.O)
fun getRelativeTime(dateString: String?): String {
    if (dateString.isNullOrBlank()) return "Fecha no disponible"
    return try {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")
        val dateTime = LocalDateTime.parse(dateString, formatter)
        val now = LocalDateTime.now()
        val totalMinutes = ChronoUnit.MINUTES.between(now, dateTime)
        val absMinutes = kotlin.math.abs(totalMinutes)
        val hours = absMinutes / 60
        val days = absMinutes / (60 * 24)

        return when {
            totalMinutes > 0 -> formatRelativeTime(hours, absMinutes % 60, days, future = true)
            totalMinutes < 0 -> formatRelativeTime(hours, absMinutes % 60, days, future = false)
            else -> "Ahora"
        }
    } catch (e: Exception) {
        "Fecha inválida"
    }
}

private fun formatRelativeTime(hours: Long, minutes: Long, days: Long, future: Boolean): String {
    val timePrefix = if (future) "En" else "Hace"
    return when {
        days >= 1 -> "$timePrefix $days día${if (days > 1) "s" else ""}"
        hours >= 1 -> "$timePrefix $hours hora${if (hours > 1) "s" else ""}"
        else -> "$timePrefix $minutes minuto${if (minutes > 1) "s" else ""}"
    }
}