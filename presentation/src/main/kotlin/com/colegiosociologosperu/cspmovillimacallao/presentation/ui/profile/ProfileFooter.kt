package com.colegiosociologosperu.cspmovillimacallao.presentation.ui.profile

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.colegiosociologosperu.cspmovillimacallao.presentation.utils.theme.Red_Dark
import com.colegiosociologosperu.cspmovillimacallao.presentation.utils.theme.Typography

@Composable
fun ProfileFooter(
    modifier: Modifier = Modifier,
    onSignOut: () -> Unit,
    onDeleteAccount: () -> Unit
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(modifier = Modifier.height(32.dp))
        Button(
            onClick = onSignOut,
            colors = ButtonDefaults.textButtonColors(Red_Dark),
        ) {
            Text(
                text = "Cerrar sesión",
                color = Color.White,
                style = Typography.bodyMedium
            )
        }
        Button(
            onClick = onDeleteAccount,
            colors = ButtonDefaults.textButtonColors(Red_Dark),
        ) {
            Text(
                text = "Eliminar Cuenta",
                color = Color.White,
                style = Typography.bodyMedium
            )
        }
    }
}

@Preview
@Composable
private fun ProfileFooterPreview() {
    ProfileFooter(
        onSignOut = {},
        onDeleteAccount = {}
    )
}