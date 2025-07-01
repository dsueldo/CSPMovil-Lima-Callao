package com.colegiosociologosperu.cspmovillimacallao.presentation.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.colegiosociologosperu.cspmovillimacallao.presentation.utils.theme.Typography

@Composable
fun ItemProfileComponent(
    title: String,
    content: String,
    imageVector: ImageVector,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        Icon(
            imageVector = imageVector,
            contentDescription = "Icon"
        )

        Column(modifier = Modifier.fillMaxWidth()) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = title,
                style = Typography.bodyMedium
            )
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = content,
                style = Typography.bodySmall,
            )
        }
    }
}

@Preview
@Composable
private fun ItemProfileComponentPreview() {
    ItemProfileComponent(
        imageVector = Icons.Outlined.AccountCircle,
        title = "Nombre",
        content = "Christian Quispe"
    )
}