package com.colegiosociologosperu.cspmovillimacallao.presentation.ui.payment.instruction

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.colegiosociologosperu.cspmovillimacallao.presentation.utils.theme.Typography

@Composable
fun PaymentInstructionHeader(modifier: Modifier = Modifier) {
    Text(
        modifier = modifier,
        text = "Instrucciones de Pago",
        textAlign = TextAlign.Center,
        style = Typography.titleLarge
    )
}

@Preview
@Composable
private fun PaymentInstructionHeaderPreview() {
    PaymentInstructionHeader()
}