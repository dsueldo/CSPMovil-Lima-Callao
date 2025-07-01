package com.colegiosociologosperu.cspmovillimacallao.presentation.ui.auth.changepassword

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.colegiosociologosperu.cspmovillimacallao.presentation.utils.theme.Typography
import com.colegiosociologosperu.cspmovillimacallao.presentation.R

@Composable
fun ChangePasswordBody(
    modifier: Modifier = Modifier,

    email: TextFieldValue,
    confirmEmail: TextFieldValue,

    emailValueChange: (TextFieldValue) -> Unit,
    confirmEmailValueChange: (TextFieldValue) -> Unit,

    emailFocusRequester: FocusRequester,
    confirmEmailFocusRequester: FocusRequester,
) {

    val emailMatch = email.text == confirmEmail.text

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.End,
    ) {

        OutlinedTextField(
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .focusRequester(emailFocusRequester),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color.Black,
                unfocusedBorderColor = Color.Black,
                cursorColor = Color.Black
            ),
            value = email,
            onValueChange = emailValueChange,
            label = {
                Text(
                    text = stringResource(R.string.login_screen_email),
                    style = Typography.bodySmall,
                    color = Color.Black,
                )
            },
            placeholder = {
                Text(
                    stringResource(R.string.login_screen_enter_email),
                    style = Typography.bodySmall,
                    color = Color.Black,
                )
            },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Email,
                    contentDescription = stringResource(R.string.email),
                    tint = Color.Black
                )
            },
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next, keyboardType = KeyboardType.Email),
            keyboardActions = KeyboardActions(onNext = { emailFocusRequester.requestFocus() }),
            textStyle = Typography.bodySmall,
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .focusRequester(confirmEmailFocusRequester),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color.Black,
                unfocusedBorderColor = Color.Black,
                cursorColor = Color.Black
            ),
            value = confirmEmail,
            onValueChange = confirmEmailValueChange,
            label = {
                Text(
                    text = stringResource(R.string.login_screen_confirm_email),
                    style = Typography.bodySmall,
                    color = Color.Black,
                )
            },
            placeholder = {
                Text(
                    stringResource(R.string.login_screen_enter_email),
                    style = Typography.bodySmall,
                    color = Color.Black,
                )
            },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Email,
                    contentDescription = stringResource(R.string.email),
                    tint = Color.Black
                )
            },
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done, keyboardType = KeyboardType.Email),
            keyboardActions = KeyboardActions(onDone = {}),
            textStyle = Typography.bodySmall
        )

        if (!emailMatch) {
            Text(
                text = "Los correos no coinciden",
                color = Color.Red
            )
        }
    }
}

@Preview
@Composable
private fun ChangePasswordBodyPreview() {
    ChangePasswordBody(
        email = TextFieldValue(""),
        confirmEmail = TextFieldValue(""),
        emailValueChange = {},
        confirmEmailValueChange = {},
        emailFocusRequester = FocusRequester(),
        confirmEmailFocusRequester = FocusRequester(),
    )
}