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
        val minutes = absMinutes % 60

        return when {
            totalMinutes > 0 && hours < 24 -> formatRelativeTime(hours, minutes, future = true)
            totalMinutes < 0 && hours < 24 -> formatRelativeTime(hours, minutes, future = false)
            else -> dateTime.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
        }
    } catch (e: Exception) {
        "Fecha inválida"
    }
}

private fun formatRelativeTime(hours: Long, minutes: Long, future: Boolean): String {
    val timePrefix = if (future) "En" else "Hace"
    return when {
        hours == 1L -> "$timePrefix 1 hora"
        hours > 1 -> "$timePrefix $hours horas"
        minutes == 1L -> "$timePrefix 1 minuto"
        else -> "$timePrefix $minutes minutos"
    }
}