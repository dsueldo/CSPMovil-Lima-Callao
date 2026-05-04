package com.colegiosociologosperu.cspmovillimacallao.presentation.utils.time

import java.time.LocalDateTime
import java.time.temporal.ChronoUnit
import android.os.Build
import androidx.annotation.RequiresApi

@RequiresApi(Build.VERSION_CODES.O)
fun getRelativeTime(dateString: String?): String {
    if (dateString.isNullOrBlank()) return "Fecha no disponible"
    return try {
        // LocalDateTime.parse maneja automáticamente el formato ISO "yyyy-MM-dd'T'HH:mm:ss"
        val dateTime = LocalDateTime.parse(dateString)
        val now = LocalDateTime.now()

        val seconds = ChronoUnit.SECONDS.between(dateTime, now)
        val absSeconds = kotlin.math.abs(seconds)
        val isFuture = seconds < 0
        val prefix = if (isFuture) "En" else "Hace"

        return when {
            absSeconds < 60 -> if (isFuture) "En un momento" else "Hace un momento"
            absSeconds < 3600 -> {
                val minutes = absSeconds / 60
                "$prefix $minutes minuto${if (minutes != 1L) "s" else ""}"
            }
            absSeconds < 86400 -> {
                val hours = absSeconds / 3600
                "$prefix $hours hora${if (hours != 1L) "s" else ""}"
            }
            absSeconds < 604800 -> { // 7 días
                val days = absSeconds / 86400
                "$prefix $days día${if (days != 1L) "s" else ""}"
            }
            absSeconds < 2592000 -> { // 30 días
                val weeks = absSeconds / 604800
                "$prefix $weeks semana${if (weeks != 1L) "s" else ""}"
            }
            absSeconds < 31536000 -> { // 365 días
                val months = absSeconds / 2592000
                "$prefix $months ${if (months == 1L) "mes" else "meses"}"
            }
            else -> {
                val years = absSeconds / 31536000
                "$prefix $years año${if (years != 1L) "s" else ""}"
            }
        }
    } catch (e: Exception) {
        "Fecha inválida"
    }
}