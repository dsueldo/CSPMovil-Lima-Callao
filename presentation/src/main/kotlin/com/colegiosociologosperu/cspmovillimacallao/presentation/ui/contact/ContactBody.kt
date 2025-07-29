package com.colegiosociologosperu.cspmovillimacallao.presentation.ui.contact

import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import com.colegiosociologosperu.cspmovillimacallao.presentation.R
import com.colegiosociologosperu.cspmovillimacallao.presentation.utils.theme.Typography

@Composable
fun ContactBody(modifier: Modifier) {
    val context = LocalContext.current
    val politicaPrivacidadUrl = "https://dsueldo.github.io/CSPMovil-Lima-Callao/politica-privacidad.html"
    val correoInstitucional = "colegiodesociologoslimacallao@gmail.com"
    val numeroInstitucional = "+51 961 289 348"
    val sitioWeb = "https://csp-limacallao.org.pe/"

    Column(
        modifier = modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Image(
            modifier = Modifier
                .fillMaxWidth(),
            painter = painterResource(id = R.drawable.ic_logo),
            contentDescription = null
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Correo",
            style = Typography.bodyLarge,
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp,
            textAlign = TextAlign.Center,
        )
        Text(
            text = "colegiodesociologoslimacallao@gmail.com",
            modifier = Modifier
                .clickable {
                    val intent = Intent(Intent.ACTION_SENDTO, "mailto:$correoInstitucional".toUri())
                    context.startActivity(intent)
                }
                .padding(vertical = 8.dp),
            style = Typography.bodyMedium,
            fontSize = 16.sp,
            textAlign = TextAlign.Center,
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Teléfono",
            style = Typography.bodyLarge,
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp,
            textAlign = TextAlign.Center,
        )
        Text(
            text = "+51 961 289 348",
            modifier = Modifier
                .clickable {
                    val intent = Intent(Intent.ACTION_DIAL, ("tel:$numeroInstitucional").toUri())
                    context.startActivity(intent)
                }
                .padding(vertical = 8.dp),
            style = Typography.bodyMedium,
            fontSize = 16.sp,
            textAlign = TextAlign.Center,
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Sitio web",
            style = Typography.bodyLarge,
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp,
            textAlign = TextAlign.Center,
        )
        Text(
            text = "https://csp-limacallao.org.pe/",
            modifier = Modifier
                .clickable {
                    val intent = Intent(Intent.ACTION_VIEW, sitioWeb.toUri())
                    context.startActivity(intent)
                }
                .padding(vertical = 8.dp),
            style = Typography.bodyMedium,
            fontSize = 16.sp,
            textAlign = TextAlign.Center,
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Política de Privacidad",
            style = Typography.bodyLarge,
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp,
            textAlign = TextAlign.Center,
        )
        Text(
            text = "*****Política de Privacidad*****",
            modifier = Modifier
                .clickable {
                    val intent = Intent(Intent.ACTION_VIEW, politicaPrivacidadUrl.toUri())
                    context.startActivity(intent)
                }
                .padding(vertical = 8.dp),
            style = Typography.bodyMedium,
            fontSize = 16.sp,
            textAlign = TextAlign.Center,
        )
    }
}