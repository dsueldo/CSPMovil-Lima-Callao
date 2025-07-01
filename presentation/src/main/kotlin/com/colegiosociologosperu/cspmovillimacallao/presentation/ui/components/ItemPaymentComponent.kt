package com.colegiosociologosperu.cspmovillimacallao.presentation.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.colegiosociologosperu.cspmovillimacallao.presentation.utils.theme.Typography

@Composable
fun ItemPaymentComponent(
    image: String,
    title: String,
    content: String,
    note: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {

    Row(
        modifier = modifier
            .clickable { onClick() }
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        Column(verticalArrangement = Arrangement.spacedBy(24.dp)) {

            Row {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = title,
                        style = Typography.bodyLarge
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = content,
                        style = Typography.bodyMedium,
                        color = Color.Gray
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    if (note.isNotEmpty()) {
                        Text(
                            text = note,
                            style = Typography.bodySmall,
                            color = Color.Gray
                        )
                    }
                }

                Icon(
                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                    contentDescription = "Arrow",
                    tint = Color.Black
                )
            }

            HorizontalDivider(
                thickness = 1.dp,
                color = Color.LightGray
            )
        }
    }
}

@Preview
@Composable
private fun ItemPaymentComponentPreview() {
    ItemPaymentComponent(
        image = "url",
        title = "Cuotas Sociales Ordinarias",
        content = "Contenido",
        note = "Nota",
        onClick = {}
    )
}
