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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.colegiosociologosperu.cspmovillimacallao.presentation.utils.theme.Red_Dark
import com.colegiosociologosperu.cspmovillimacallao.presentation.utils.theme.Typography

@Composable
fun ProfileHeader(
    modifier: Modifier = Modifier,
    name: String,
    onEditAccount: () -> Unit,
) {

    Text(
        text = "Perfil",
        style = Typography.headlineMedium,
        fontWeight = FontWeight.Bold
    )

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = name,
            textAlign = TextAlign.Center,
            style = Typography.headlineSmall
        )

        Button(
            onClick = onEditAccount,
            colors = ButtonDefaults.textButtonColors(Red_Dark),
        ) {
            Text(
                text = "Editar Perfil",
                color = Color.White,
                style = Typography.bodyMedium
            )
        }
    }
}

@Preview
@Composable
private fun ProfileHeaderPreview() {
    ProfileHeader(
        name = "Diego",
        onEditAccount = {}
    )
}