package com.colegiosociologosperu.cspmovillimacallao.presentation.ui.components.button

import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

@Composable
fun WhatsAppButton(numeroTelefono: String, mensaje: String) {
    val context = LocalContext.current

    Button(onClick = {
        val uri = Uri.parse("https://wa.me/$numeroTelefono/?text=${Uri.encode(mensaje)}")
        val intent = Intent(Intent.ACTION_VIEW, uri)
        try {
            context.startActivity(intent)
        } catch (e: Exception) {
            // Manejar el caso en que WhatsApp no esté instalado
            // Puedes mostrar un mensaje al usuario
            e.printStackTrace()
            // Ejemplo:
            Toast.makeText(context, "WhatsApp no está instalado.", Toast.LENGTH_SHORT).show()
        }
    }) {
        Text(text = "Enviar mensaje por WhatsApp")
    }
}