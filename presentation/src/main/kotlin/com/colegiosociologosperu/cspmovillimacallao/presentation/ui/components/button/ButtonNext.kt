package com.colegiosociologosperu.cspmovillimacallao.presentation.ui.components.button

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Whatsapp
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun NextButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    ExtendedFloatingActionButton(
        modifier = modifier,
        containerColor = MaterialTheme.colorScheme.secondaryContainer,
        contentColor = MaterialTheme.colorScheme.onSecondaryContainer,
        shape = RoundedCornerShape(32.dp),
        onClick = { onClick() },
        content = {
            Text(
                text = "Ir a WhatsApp",
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.onSecondaryContainer
            )
            Spacer(modifier = Modifier.padding(horizontal = 4.dp))
            Icon(
                Icons.Filled.Whatsapp,
                contentDescription = "Botón de WhatsApp",
                tint = MaterialTheme.colorScheme.onSecondaryContainer
            )
        }
    )
}

@Preview
@Composable
private fun NextButtonPreview() {
    NextButton(
        onClick = {}
    )
}