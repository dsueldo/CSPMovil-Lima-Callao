package com.colegiosociologosperu.cspmovillimacallao.presentation.ui.profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Badge
import androidx.compose.material.icons.outlined.CalendarToday
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Fingerprint
import androidx.compose.material.icons.outlined.History
import androidx.compose.material.icons.outlined.People
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Phone
import androidx.compose.material.icons.outlined.School
import androidx.compose.material.icons.outlined.VerifiedUser
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.colegiosociologosperu.cspmovillimacallao.domain.entities.user.ProfileUiState
import com.colegiosociologosperu.cspmovillimacallao.presentation.ui.components.ItemProfileComponent
import com.colegiosociologosperu.cspmovillimacallao.presentation.utils.theme.Red_Dark

@Composable
fun ProfileBody(
    modifier: Modifier = Modifier,
    profileUiState: ProfileUiState,
    email: String,
) {
    Column(
        modifier = modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        ProfileSection(title = "Información Personal") {
            ItemProfileComponent(
                imageVector = Icons.Outlined.Person,
                title = "Nombres y Apellidos",
                content = "${profileUiState.name} ${profileUiState.lastName}"
            )

            ItemProfileComponent(
                imageVector = Icons.Outlined.Phone,
                title = "Celular",
                content = profileUiState.phone
            )

            ItemProfileComponent(
                imageVector = Icons.Outlined.Email,
                title = "Correo",
                content = email
            )

            ItemProfileComponent(
                imageVector = Icons.Outlined.People,
                title = "Género",
                content = profileUiState.gender
            )

            ItemProfileComponent(
                imageVector = Icons.Outlined.CalendarToday,
                title = "Cumpleaños",
                content = profileUiState.birthday
            )

            ItemProfileComponent(
                imageVector = Icons.Outlined.Fingerprint,
                title = "DNI",
                content = profileUiState.dni
            )
        }

        ProfileSection(title = "Información Profesional") {
            ItemProfileComponent(
                imageVector = Icons.Outlined.Badge,
                title = "Número de Colegiatura",
                content = profileUiState.codeNumber
            )

            ItemProfileComponent(
                imageVector = Icons.Outlined.School,
                title = "Especialidad",
                content = profileUiState.specialized
            )

            ItemProfileComponent(
                imageVector = Icons.Outlined.VerifiedUser,
                title = "Condición",
                content = profileUiState.condition
            )

            ItemProfileComponent(
                imageVector = Icons.Outlined.History,
                title = "Último periodo pagado",
                content = profileUiState.payLastPeriod
            )
        }
    }
}

@Composable
private fun ProfileSection(
    title: String,
    content: @Composable () -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = Red_Dark,
            modifier = Modifier.padding(horizontal = 4.dp)
        )
        Card(
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f)
            ),
            elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        ) {
            Column(modifier = Modifier.padding(vertical = 8.dp)) {
                content()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProfileBodyPreview() {
    ProfileBody(
        profileUiState = ProfileUiState(
            name = "Christian",
            lastName = "Quispe",
            phone = "987654321",
            gender = "Masculino",
            birthday = "01/01/1990",
            dni = "12345678",
            codeNumber = "4567",
            specialized = "Sociología",
            condition = "Habilitado",
            payLastPeriod = "2024-I"
        ),
        email = "christian@example.com"
    )
}
