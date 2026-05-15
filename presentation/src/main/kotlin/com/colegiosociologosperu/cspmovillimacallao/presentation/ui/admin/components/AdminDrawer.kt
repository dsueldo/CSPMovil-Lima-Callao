package com.colegiosociologosperu.cspmovillimacallao.presentation.ui.admin.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AdminPanelSettings
import androidx.compose.material.icons.filled.Newspaper
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.colegiosociologosperu.cspmovillimacallao.domain.entities.user.ProfileUiState
import com.colegiosociologosperu.cspmovillimacallao.presentation.utils.theme.Red_Dark
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun AdminDrawerContent(
    admin: ProfileUiState?,
    selectedRoute: String,
    onNavigateToAdmin: () -> Unit,
    onNavigateToNews: () -> Unit,
    modifier: Modifier = Modifier
) {
    ModalDrawerSheet(modifier = modifier.width(300.dp)) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Red_Dark)
                .padding(24.dp)
        ) {
            if (admin?.urlImageProfile?.isNotEmpty() == true) {
                AsyncImage(
                    model = admin.urlImageProfile,
                    contentDescription = null,
                    modifier = Modifier
                        .size(64.dp)
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop
                )
            } else {
                Surface(
                    modifier = Modifier.size(64.dp),
                    shape = CircleShape,
                    color = Color.White.copy(alpha = 0.2f)
                ) {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = null,
                        modifier = Modifier.padding(12.dp),
                        tint = Color.White
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = admin?.let { "${it.name} ${it.lastName}" } ?: "Administrador",
                color = Color.White,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "DNI: ${admin?.dni ?: "N/A"}",
                color = Color.White.copy(alpha = 0.8f),
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = "Código: ${admin?.codeNumber ?: "N/A"}",
                color = Color.White.copy(alpha = 0.8f),
                style = MaterialTheme.typography.bodyMedium
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        NavigationDrawerItem(
            label = { Text("Administración") },
            selected = selectedRoute == "admin",
            onClick = onNavigateToAdmin,
            icon = { Icon(Icons.Default.AdminPanelSettings, contentDescription = null) },
            modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
        )

        NavigationDrawerItem(
            label = { Text("Noticias") },
            selected = selectedRoute == "news",
            onClick = onNavigateToNews,
            icon = { Icon(Icons.Default.Newspaper, contentDescription = null) },
            modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun AdminDrawerContentPreview() {
    AdminDrawerContent(
        admin = ProfileUiState(
            name = "Christian",
            lastName = "Quispe",
            dni = "12345678",
            codeNumber = "9876",
            urlImageProfile = ""
        ),
        selectedRoute = "admin",
        onNavigateToAdmin = {},
        onNavigateToNews = {}
    )
}
