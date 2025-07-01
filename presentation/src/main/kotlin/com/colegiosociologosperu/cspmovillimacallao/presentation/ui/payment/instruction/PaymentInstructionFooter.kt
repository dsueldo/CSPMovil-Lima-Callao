package com.colegiosociologosperu.cspmovillimacallao.presentation.ui.payment.instruction

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.colegiosociologosperu.cspmovillimacallao.presentation.ui.components.button.FooterButtonComponent

@Composable
fun PaymentInstructionFooter(
    onBack: () -> Unit,
    onNavigateWhatsapp: () -> Unit,
    modifier: Modifier = Modifier
) {
    FooterButtonComponent(
        modifier = modifier,
        onBack = onBack,
        onNavigateWhatsapp = onNavigateWhatsapp,
    )
}

@Preview
@Composable
private fun PaymentInstructionFooterPreview() {
    PaymentInstructionFooter(
        onBack = {},
        onNavigateWhatsapp = {}
    )
}