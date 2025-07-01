package com.colegiosociologosperu.cspmovillimacallao.presentation.ui.editprofile

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardActions
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.colegiosociologosperu.cspmovillimacallao.presentation.utils.theme.Typography
import com.colegiosociologosperu.cspmovillimacallao.presentation.viewmodels.editprofile.EditProfileViewModel

@Composable
fun EditProfileBody(
    modifier: Modifier = Modifier,
    viewModel: EditProfileViewModel,
) {
    val profileUiState by viewModel.profileUiState.collectAsState()
    var showBirthdayPicker by remember { mutableStateOf(false) }

    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.End,
    ) {
        OutlinedTextField(
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color.Black,
                unfocusedBorderColor = Color.Black,
                cursorColor = Color.Black
            ),
            value = profileUiState.name,
            onValueChange = { viewModel.updateName(it) },
            label = {
                Text(
                    text = "Nombres",
                    style = Typography.bodySmall,
                    color = Color.Black,
                )
            },
            placeholder = {
                Text(
                    text = "Ingrese sus nombres",
                    style = Typography.bodySmall,
                    color = Color.Black,
                )
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            keyboardActions = KeyboardActions(onNext = {}),
            textStyle = Typography.bodySmall
        )

        OutlinedTextField(
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color.Black,
                unfocusedBorderColor = Color.Black,
                cursorColor = Color.Black
            ),
            value = profileUiState.lastName,
            onValueChange = { viewModel.updateLastName(it) },
            label = {
                Text(
                    text = "Apellidos",
                    style = Typography.bodySmall,
                    color = Color.Black,
                )
            },
            placeholder = {
                Text(
                    text = "Ingrese sus apellidos",
                    style = Typography.bodySmall,
                    color = Color.Black,
                )
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            keyboardActions = KeyboardActions(onNext = {}),
            textStyle = Typography.bodySmall
        )

        OutlinedTextField(
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color.Black,
                unfocusedBorderColor = Color.Black,
                cursorColor = Color.Black
            ),
            value = profileUiState.phone,
            onValueChange = { viewModel.updatePhoneNumber(it) },
            label = {
                Text(
                    text = "Celular",
                    style = Typography.bodySmall,
                    color = Color.Black,
                )
            },
            placeholder = {
                Text(
                    text = "Ingrese su celular",
                    style = Typography.bodySmall,
                    color = Color.Black,
                )
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
            keyboardActions = KeyboardActions(onNext = {}),
            textStyle = Typography.bodySmall
        )

        OutlinedTextField(
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color.Black,
                unfocusedBorderColor = Color.Black,
                cursorColor = Color.Black
            ),
            value = profileUiState.email,
            onValueChange = { viewModel.updateEmail(it) },
            label = {
                Text(
                    text = "Correo",
                    style = Typography.bodySmall,
                    color = Color.Black,
                )
            },
            placeholder = {
                Text(
                    text = "Ingrese su correo",
                    style = Typography.bodySmall,
                    color = Color.Black,
                )
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            keyboardActions = KeyboardActions(onNext = {}),
            textStyle = Typography.bodySmall
        )

        Spacer(modifier = Modifier.height(8.dp))

        GenderComponent(
            selectedGender = profileUiState.gender,
            onGenderSelected = { viewModel.updateGender(it) }
        )

        if (showBirthdayPicker) {
            BirthdayComponent(
                onDateSelected = { formattedDate ->
                    viewModel.updateBirthday(formattedDate)
                },
                onDismiss = { showBirthdayPicker = false },
            )
        }

        OutlinedTextField(
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color.Black,
                unfocusedBorderColor = Color.Black,
                cursorColor = Color.Black
            ),
            value = profileUiState.birthday,
            onValueChange = { },
            label = {
                Text(
                    text = "Cumpleaños",
                    style = Typography.bodySmall,
                    color = Color.Black,
                )
            },
            placeholder = {
                Text(
                    text = "Ingrese su cumpleaños",
                    style = Typography.bodySmall,
                    color = Color.Black,
                )
            },
            readOnly = true,
            trailingIcon = {
                TextButton(onClick = { showBirthdayPicker = true }) {
                    Text(
                        text = "Seleccionar",
                        color = Color.Black,
                        style = Typography.bodySmall
                    )
                }
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            keyboardActions = KeyboardActions(onNext = {}),
            textStyle = Typography.bodySmall
        )

        OutlinedTextField(
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color.Black,
                unfocusedBorderColor = Color.Black,
                cursorColor = Color.Black
            ),
            value = profileUiState.dni,
            onValueChange = { viewModel.updateDni(it) },
            label = {
                Text(
                    text = "DNI",
                    style = Typography.bodySmall,
                    color = Color.Black,
                )
            },
            placeholder = {
                Text(
                    text = "Ingrese su DNI",
                    style = Typography.bodySmall,
                    color = Color.Black,
                )
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            keyboardActions = KeyboardActions(onNext = {}),
            textStyle = Typography.bodySmall
        )

        OutlinedTextField(
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color.Black,
                unfocusedBorderColor = Color.Black,
                cursorColor = Color.Black
            ),
            value = profileUiState.codeNumber,
            onValueChange = { viewModel.updateCodeNumber(it) },
            label = {
                Text(
                    text = "Número de Colegiatura",
                    style = Typography.bodySmall,
                    color = Color.Black,
                )
            },
            placeholder = {
                Text(
                    text = "Ingrese su Número de Colegiatura",
                    style = Typography.bodySmall,
                    color = Color.Black,
                )
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            keyboardActions = KeyboardActions(onNext = {}),
            textStyle = Typography.bodySmall
        )

        OutlinedTextField(
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color.Black,
                unfocusedBorderColor = Color.Black,
                cursorColor = Color.Black
            ),
            value = profileUiState.specialized,
            onValueChange = { viewModel.updateSpecialized(it) },
            label = {
                Text(
                    text = "Especialidad",
                    style = Typography.bodySmall,
                    color = Color.Black,
                )
            },
            placeholder = {
                Text(
                    text = "Ingrese su Especialidad",
                    style = Typography.bodySmall,
                    color = Color.Black,
                )
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            keyboardActions = KeyboardActions(onNext = {}),
            textStyle = Typography.bodySmall
        )
    }
}