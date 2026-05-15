package com.colegiosociologosperu.cspmovillimacallao.presentation.ui.editprofile

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.colegiosociologosperu.cspmovillimacallao.domain.entities.user.ProfileUiState
import com.colegiosociologosperu.cspmovillimacallao.presentation.utils.theme.Red_Dark
import com.colegiosociologosperu.cspmovillimacallao.presentation.utils.theme.Typography
import com.colegiosociologosperu.cspmovillimacallao.presentation.viewmodels.editprofile.EditProfileViewModel

@Composable
fun EditProfileBody(
    modifier: Modifier = Modifier,
    viewModel: EditProfileViewModel,
) {
    val profileUiState by viewModel.profileUiState.collectAsState()

    EditProfileBodyContent(
        modifier = modifier,
        profileUiState = profileUiState,
        onUpdateName = { viewModel.updateName(it) },
        onUpdateLastName = { viewModel.updateLastName(it) },
        onUpdatePhone = { viewModel.updatePhoneNumber(it) },
        onUpdateEmail = { viewModel.updateEmail(it) },
        onUpdateGender = { viewModel.updateGender(it) },
        onUpdateBirthday = { viewModel.updateBirthday(it) },
        onUpdateDni = { viewModel.updateDni(it) },
        onUpdateCodeNumber = { viewModel.updateCodeNumber(it) },
        onUpdateSpecialized = { viewModel.updateSpecialized(it) }
    )
}

@Composable
fun EditProfileBodyContent(
    modifier: Modifier = Modifier,
    profileUiState: ProfileUiState,
    onUpdateName: (String) -> Unit,
    onUpdateLastName: (String) -> Unit,
    onUpdatePhone: (String) -> Unit,
    onUpdateEmail: (String) -> Unit,
    onUpdateGender: (String) -> Unit,
    onUpdateBirthday: (String) -> Unit,
    onUpdateDni: (String) -> Unit,
    onUpdateCodeNumber: (String) -> Unit,
    onUpdateSpecialized: (String) -> Unit,
) {
    var showBirthdayPicker by remember { mutableStateOf(false) }

    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        val textFieldColors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = Red_Dark,
            focusedLabelColor = Red_Dark,
            cursorColor = Red_Dark
        )

        OutlinedTextField(
            value = profileUiState.name,
            onValueChange = onUpdateName,
            label = { Text("Nombres") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            colors = textFieldColors,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            textStyle = Typography.bodyMedium
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = profileUiState.lastName,
            onValueChange = onUpdateLastName,
            label = { Text("Apellidos") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            colors = textFieldColors,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            textStyle = Typography.bodyMedium
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = profileUiState.phone,
            onValueChange = { phone ->
                if (phone.length <= 9 && phone.all { it.isDigit() }) {
                    onUpdatePhone(phone)
                }
            },
            label = { Text("Celular") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            colors = textFieldColors,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
            textStyle = Typography.bodyMedium
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = profileUiState.email,
            onValueChange = onUpdateEmail,
            label = { Text("Correo") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            colors = textFieldColors,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            textStyle = Typography.bodyMedium
        )

        Spacer(modifier = Modifier.height(16.dp))

        GenderComponent(
            selectedGender = profileUiState.gender,
            onGenderSelected = onUpdateGender
        )

        if (showBirthdayPicker) {
            BirthdayComponent(
                onDateSelected = { formattedDate ->
                    onUpdateBirthday(formattedDate)
                    showBirthdayPicker = false
                },
                onDismiss = { showBirthdayPicker = false },
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = profileUiState.birthday,
            onValueChange = { },
            label = { Text("Cumpleaños") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            readOnly = true,
            colors = textFieldColors,
            trailingIcon = {
                TextButton(onClick = { showBirthdayPicker = true }) {
                    Text(
                        text = "Seleccionar",
                        color = Red_Dark,
                        style = Typography.labelLarge
                    )
                }
            },
            textStyle = Typography.bodyMedium
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = profileUiState.dni,
            onValueChange = { dni ->
                if (dni.length <= 8 && dni.all { it.isDigit() }) {
                    onUpdateDni(dni)
                }
            },
            label = { Text("DNI") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            colors = textFieldColors,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            textStyle = Typography.bodyMedium
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = profileUiState.codeNumber,
            onValueChange = { code ->
                if (code.length <= 10 && code.all { it.isDigit() }) {
                    onUpdateCodeNumber(code)
                }
            },
            label = { Text("Número de Colegiatura") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            colors = textFieldColors,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            textStyle = Typography.bodyMedium
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = profileUiState.specialized,
            onValueChange = onUpdateSpecialized,
            label = { Text("Especialidad") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            colors = textFieldColors,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            textStyle = Typography.bodyMedium
        )
        
        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Preview(showBackground = true)
@Composable
fun EditProfileBodyPreview() {
    EditProfileBodyContent(
        profileUiState = ProfileUiState(
            name = "Christian",
            lastName = "Quispe",
            phone = "987654321",
            email = "christian@example.com"
        ),
        onUpdateName = {},
        onUpdateLastName = {},
        onUpdatePhone = {},
        onUpdateEmail = {},
        onUpdateGender = {},
        onUpdateBirthday = {},
        onUpdateDni = {},
        onUpdateCodeNumber = {},
        onUpdateSpecialized = {}
    )
}
