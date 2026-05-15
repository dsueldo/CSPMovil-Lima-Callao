package com.colegiosociologosperu.cspmovillimacallao.presentation.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.AsyncImage
import com.colegiosociologosperu.cspmovillimacallao.domain.entities.user.ProfileUiState
import com.colegiosociologosperu.cspmovillimacallao.presentation.utils.theme.Red_Dark

@Composable
fun UserItem(
    user: ProfileUiState,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (user.urlImageProfile.isNotEmpty()) {
                AsyncImage(
                    model = user.urlImageProfile,
                    contentDescription = null,
                    modifier = Modifier
                        .size(50.dp)
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop
                )
            } else {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = null,
                    modifier = Modifier
                        .size(50.dp)
                        .clip(CircleShape)
                        .padding(8.dp),
                    tint = Red_Dark
                )
            }

            Column(
                modifier = Modifier
                    .padding(start = 16.dp)
                    .weight(1f)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "${user.name} ${user.lastName}",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.weight(1f)
                    )
                    
                    val isEnabled = user.condition.equals("HABILITADO", ignoreCase = true)
                    val statusColor = if (isEnabled) Color(0xFF4CAF50) else Color(0xFFE57373)
                    val statusText = if (isEnabled) "HABILITADO" else "DESHABILITADO"

                    Surface(
                        color = statusColor.copy(alpha = 0.1f),
                        shape = MaterialTheme.shapes.extraSmall,
                        border = androidx.compose.foundation.BorderStroke(1.dp, statusColor)
                    ) {
                        Text(
                            text = statusText,
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                            style = MaterialTheme.typography.labelSmall,
                            color = statusColor,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
                Text(
                    text = "DNI: ${user.dni} | Código: ${user.codeNumber}",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray
                )
                if (user.specialized.isNotEmpty()) {
                    Text(
                        text = user.specialized,
                        style = MaterialTheme.typography.bodySmall,
                        color = Red_Dark,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun UserItemPreview() {
    UserItem(
        user = ProfileUiState(
            name = "Christian",
            lastName = "Quispe",
            dni = "12345678",
            codeNumber = "9876",
            specialized = "Sociología Urbana",
            condition = "HABILITADO"
        ),
        onClick = {}
    )
}

@Preview(showBackground = true)
@Composable
fun UserItemDisabledPreview() {
    UserItem(
        user = ProfileUiState(
            name = "Juan",
            lastName = "Perez",
            dni = "87654321",
            codeNumber = "4321",
            specialized = "Sociología Rural",
            condition = "DESHABILITADO"
        ),
        onClick = {}
    )
}
