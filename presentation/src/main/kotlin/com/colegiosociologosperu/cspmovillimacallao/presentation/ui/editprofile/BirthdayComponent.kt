package com.colegiosociologosperu.cspmovillimacallao.presentation.ui.editprofile

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.colegiosociologosperu.cspmovillimacallao.presentation.utils.theme.Red_Dark
import com.colegiosociologosperu.cspmovillimacallao.presentation.utils.theme.Typography
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BirthdayComponent(
    onDateSelected: (String) -> Unit,
    onDismiss: () -> Unit,
) {

    val state = rememberDatePickerState(
        initialDisplayMode = DisplayMode.Input
    )

    // Define custom colors for the DatePicker
    val customColors = DatePickerDefaults.colors(
        //Colores Generales
        containerColor = Color.White, //Fondo del DatePicker
        titleContentColor = Color.Red, //Color del Titulo
        headlineContentColor = Color.Black, //Color del encabezado

        //Modo Input
        subheadContentColor = Color.Yellow, //Color del texto en el modo Input
        dividerColor = Color.Black, //Color del separador
        yearContentColor = Color.Black,
        disabledYearContentColor = Color.Black,
        selectedYearContentColor = Color.White,
        selectedYearContainerColor = Red_Dark,

        //Modo Display
        selectedDayContainerColor = Red_Dark,
        selectedDayContentColor = Color.White,

        todayContentColor = Color.Black, //Color del dia por defecto
        todayDateBorderColor = Color.Transparent,
        dayContentColor = Color.Black,

        weekdayContentColor = Color.Black,
        navigationContentColor = Color.Black,
        currentYearContentColor = Color.Black,
        disabledSelectedDayContainerColor = Color.Black,
        disabledSelectedYearContentColor = Color.Black,
        disabledDayContentColor = Color.Black,
        disabledSelectedDayContentColor = Color.Black,
        dayInSelectionRangeContentColor = Color.Black,
        dayInSelectionRangeContainerColor = Color.Black,

    )

    DatePickerDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = {
                val date = state.selectedDateMillis
                date?.let {
                    val localDate = Instant.ofEpochMilli(it)
                        .atZone(ZoneId.of("UTC"))
                        .toLocalDate()
                    val formattedDate = localDate
                        .format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
                    onDateSelected(formattedDate)
                }
                onDismiss()
            }) {
                Text(text = "OK", color = Color.Black)
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(text = "Cancelar", color = Color.Black)
            }
        },
        content = {
            DatePicker(
                state = state,
                title = {
                    Text(
                        text = "Cumpleaños",
                        color = Color.Black,
                        style = Typography.titleLarge,
                        modifier = Modifier.padding(16.dp)
                    )
                },
                headline = {
                    Text(
                        text = "Selecciona tu fecha de nacimiento",
                        color = Color.Black,
                        style = Typography.bodyLarge,
                        modifier = Modifier.padding(16.dp)
                    )
                },
                colors = customColors,
            )
        },
    )
}

@Preview
@Composable
private fun BirthdayComponentPreview() {
    BirthdayComponent(
        onDateSelected = {},
        onDismiss = {}
    )
}