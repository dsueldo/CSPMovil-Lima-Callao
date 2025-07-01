package com.colegiosociologosperu.cspmovillimacallao.presentation.ui.components.button

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun FooterButtonComponent(
    onBack: () -> Unit,
    onNavigateWhatsapp: () -> Unit,
    modifier: Modifier = Modifier
) {

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        ButtonBack(onClick = onBack)
        NextButton(onClick = onNavigateWhatsapp)
    }

}

@Preview
@Composable
private fun FooterButtonComponentPreview() {
    FooterButtonComponent(
        onBack = {},
        onNavigateWhatsapp = {}
    )
}