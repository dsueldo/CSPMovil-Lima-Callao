package com.colegiosociologosperu.cspmovillimacallao.presentation.ui.components

import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.colegiosociologosperu.cspmovillimacallao.presentation.utils.theme.Red_Dark
import com.colegiosociologosperu.cspmovillimacallao.presentation.utils.theme.Typography

@Composable
fun ItemProfileComponent(
    title: String,
    content: String,
    imageVector: ImageVector,
    modifier: Modifier = Modifier
) {
    ListItem(
        modifier = modifier,
        headlineContent = {
            Text(
                text = title,
                style = Typography.labelMedium,
                color = Red_Dark,
                fontWeight = FontWeight.Bold
            )
        },
        supportingContent = {
            Text(
                text = content.ifEmpty { "No especificado" },
                style = Typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurface
            )
        },
        leadingContent = {
            Icon(
                imageVector = imageVector,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.secondary
            )
        },
        colors = ListItemDefaults.colors(
            containerColor = Color.Transparent
        )
    )
}

@Preview
@Composable
private fun ItemProfileComponentPreview() {
    ItemProfileComponent(
        imageVector = Icons.Outlined.AccountCircle,
        title = "Nombre",
        content = "Juan Perez"
    )
}