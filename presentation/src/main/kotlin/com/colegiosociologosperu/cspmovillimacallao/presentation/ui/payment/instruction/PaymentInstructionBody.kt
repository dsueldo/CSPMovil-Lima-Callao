package com.colegiosociologosperu.cspmovillimacallao.presentation.ui.payment.instruction

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.colegiosociologosperu.cspmovillimacallao.presentation.utils.theme.Typography

@Composable
fun PaymentInstructionBody(modifier: Modifier = Modifier) {

    Column(modifier = modifier) {

        Text(
            text = "Nombre del Banco",
            style = Typography.bodyLarge,
            color = MaterialTheme.colorScheme.onBackground
        )

        Text(
            text = "Banco Interbank",
            style = Typography.bodyMedium,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.onBackground
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Numero Cuenta",
            style = Typography.bodyLarge,
            color = MaterialTheme.colorScheme.onBackground
        )

        Text(
            text = "Cuenta Corriente: 4883374362415 \n" +
                    "Código Interbancario (CCI): 00348801337436241546\n",
            style = Typography.bodyMedium,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.onBackground
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Nombre del Titular",
            style = Typography.bodyLarge,
            color = MaterialTheme.colorScheme.onBackground
        )

        Text(
            text = "ÚNICA CUENTA CORRIENTE A NOMBRE \n" +
                    "MARTIN SOTO MENDOZA\n",
            style = Typography.bodyMedium,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.onBackground
        )
    }

}

@Preview
@Composable
private fun PaymentInstructionBodyPreview() {
    PaymentInstructionBody()
}