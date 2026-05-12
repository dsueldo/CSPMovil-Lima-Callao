package com.colegiosociologosperu.cspmovillimacallao.presentation.ui.editprofile

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.colegiosociologosperu.cspmovillimacallao.presentation.utils.theme.Red_Dark
import com.colegiosociologosperu.cspmovillimacallao.presentation.utils.theme.Typography

@Composable
fun EditProfileFooter(
    modifier: Modifier = Modifier,
    onClickSave: () -> Unit,
) {
    Button(
        modifier = modifier.fillMaxWidth(),
        onClick = onClickSave,
        colors = ButtonDefaults.buttonColors(
            containerColor = Red_Dark,
            contentColor = Color.White
        ),
        shape = MaterialTheme.shapes.medium
    ) {
        Text(
            text = "Guardar Cambios",
            style = Typography.titleSmall
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun EditProfileFooterPreview() {
    EditProfileFooter(onClickSave = {})
}
