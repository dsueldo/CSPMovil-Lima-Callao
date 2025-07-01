package com.colegiosociologosperu.cspmovillimacallao.presentation.ui.profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Divider
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CalendarToday
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Fingerprint
import androidx.compose.material.icons.outlined.People
import androidx.compose.material.icons.outlined.PersonOutline
import androidx.compose.material.icons.outlined.Phone
import androidx.compose.material.icons.outlined.School
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.colegiosociologosperu.cspmovillimacallao.presentation.ui.components.ItemProfileComponent
import com.colegiosociologosperu.cspmovillimacallao.presentation.viewmodels.profile.ProfileViewModel

@Composable
fun ProfileBody(
    modifier: Modifier = Modifier,
    viewModel: ProfileViewModel,
    email: String,
) {
    val profileUiState by viewModel.profileUiState.collectAsState()

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        Divider()

        profileUiState.let {
            ItemProfileComponent(
                imageVector = Icons.Outlined.PersonOutline,
                title = "Nombres",
                content = it.name
            )

            ItemProfileComponent(
                imageVector = Icons.Outlined.PersonOutline,
                title = "Apellidos",
                content = it.lastName
            )

            ItemProfileComponent(
                imageVector = Icons.Outlined.Phone,
                title = "Celular",
                content = it.phone
            )

            ItemProfileComponent(
                imageVector = Icons.Outlined.Email,
                title = "Correo",
                content = email
            )

            ItemProfileComponent(
                imageVector = Icons.Outlined.People,
                title = "Género",
                content = it.gender
            )

            ItemProfileComponent(
                imageVector = Icons.Outlined.CalendarToday,
                title = "Cumpleaños",
                content = it.birthday
            )

            ItemProfileComponent(
                imageVector = Icons.Outlined.Fingerprint,
                title = "DNI",
                content = it.dni
            )

            ItemProfileComponent(
                imageVector = Icons.Outlined.School,
                title = "Número de Colegiatura",
                content = it.codeNumber
            )

            ItemProfileComponent(
                imageVector = Icons.Outlined.School,
                title = "Especialidad",
                content = it.specialized
            )

            ItemProfileComponent(
                imageVector = Icons.Outlined.School,
                title = "Condición",
                content = it.condition
            )

            ItemProfileComponent(
                imageVector = Icons.Outlined.School,
                title = "Ultimo periodo pagado",
                content = it.payLastPeriod
            )
        }

        Divider()
    }
}