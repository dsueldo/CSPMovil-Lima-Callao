package com.colegiosociologosperu.cspmovillimacallao.presentation.ui.payment.instruction

import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

@Composable
fun PaymentInstructionScreen(
    navController: NavController = rememberNavController(),
    title: String,
) {
    val context = LocalContext.current

    Scaffold(
        modifier = Modifier
            .systemBarsPadding()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp),
        topBar = {
            PaymentInstructionHeader(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            )
        },
        content = { padding ->
            PaymentInstructionBody(
                modifier = Modifier
                    .padding(padding)
                    .padding(top = 16.dp)
                    .verticalScroll(rememberScrollState())
            )
        },
        bottomBar = {
            PaymentInstructionFooter(
                onBack = { navController.popBackStack() },
                onNavigateWhatsapp = {
                    val numeroTelefono = "51961289348"
                    val mensaje = "Hola, estoy interesado en realizar el pago de $title."
                    val uri = Uri.parse("https://wa.me/$numeroTelefono/?text=${Uri.encode(mensaje)}")
                    val intent = Intent(Intent.ACTION_VIEW, uri)
                    try {
                        context.startActivity(intent)
                    } catch (e: Exception) {
                        e.printStackTrace()
                        Toast.makeText(context, "WhatsApp no está instalado.", Toast.LENGTH_SHORT).show()
                    }
                }
            )
        }
    )

}

@Preview
@Composable
private fun PaymentInstructionScreenPreview() {
    PaymentInstructionScreen(title = "")
}