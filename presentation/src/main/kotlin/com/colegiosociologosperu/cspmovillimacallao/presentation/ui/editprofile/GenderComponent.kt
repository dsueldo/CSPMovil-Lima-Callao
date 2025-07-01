package com.colegiosociologosperu.cspmovillimacallao.presentation.ui.editprofile

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ExposedDropdownMenuBox
import androidx.compose.material.ExposedDropdownMenuDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.colegiosociologosperu.cspmovillimacallao.presentation.utils.theme.Typography

data class Gender(val id: String, val name: String)

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun GenderComponent(
    modifier: Modifier = Modifier,
    selectedGender: String,
    onGenderSelected: (String) -> Unit
) {

    val options = listOf(
        Gender(id = "1", name = "Hombre"),
        Gender(id = "2", name = "Mujer")
    )

    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        modifier = modifier,
        expanded = expanded,
        onExpandedChange = { expanded = !expanded }
    ) {

        TextField(
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color.Black,
                unfocusedBorderColor = Color.Black,
                cursorColor = Color.Black,
            ),
            value = selectedGender,
            onValueChange = {},
            readOnly = true,
            label = {
                Text(
                    text = "Género",
                    style = Typography.bodySmall,
                    color = Color.Black
                )
            },
            textStyle = Typography.bodySmall,
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
            }
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            options.forEach { option ->
                DropdownMenuItem(
                    text = {
                        Text(
                            text = option.name,
                            style = Typography.bodySmall
                        )
                    },
                    onClick = {
                        onGenderSelected(option.name)
                        expanded = false
                    }
                )
            }
        }
    }
}