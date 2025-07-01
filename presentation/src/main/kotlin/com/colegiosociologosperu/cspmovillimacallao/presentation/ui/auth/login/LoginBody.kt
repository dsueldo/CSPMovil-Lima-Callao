package com.colegiosociologosperu.cspmovillimacallao.presentation.ui.auth.login

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.colegiosociologosperu.cspmovillimacallao.presentation.utils.theme.Typography
import com.colegiosociologosperu.cspmovillimacallao.presentation.R

@Composable
fun LoginBody(
    onNavigateChangePassword: () -> Unit,
    modifier: Modifier = Modifier,

    email: TextFieldValue,
    password: TextFieldValue,

    emailValueChange: (TextFieldValue) -> Unit,
    passwordValueChange: (TextFieldValue) -> Unit,

    passwordVisible: Boolean = false,
    passwordVisibleValueChange: (Boolean) -> Unit,

    emailFocusRequester: FocusRequester,
    passwordFocusRequester: FocusRequester,
) {

    Column(
        modifier = modifier.fillMaxWidth(),
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
                    text = stringResource(R.string.login_screen_enter_email),
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
            keyboardActions = KeyboardActions(onNext = { passwordFocusRequester.requestFocus() }),
            textStyle = Typography.bodySmall
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .focusRequester(passwordFocusRequester),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color.Black,
                unfocusedBorderColor = Color.Black,
                cursorColor = Color.Black
            ),
            value = password,
            onValueChange = passwordValueChange,
            label = {
                Text(
                    text = stringResource(R.string.login_screen_password),
                    style = Typography.bodySmall,
                    color = Color.Black,
                )
            },
            placeholder = {
                Text(
                    text = stringResource(R.string.login_screen_enter_password),
                    style = Typography.bodySmall,
                    color = Color.Black,
                )
            },
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                IconButton(onClick = { passwordVisibleValueChange(!passwordVisible) }) {
                    Icon(
                        imageVector = if (passwordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff,
                        contentDescription =
                        if (passwordVisible)
                            stringResource(R.string.login_screen_hide_password)
                        else
                            stringResource(R.string.login_screen_show_password),
                        tint = Color.Black
                    )
                }
            },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Lock,
                    contentDescription = stringResource(R.string.lock),
                    tint = Color.Black
                )
            },
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(onDone = {}),
            textStyle = Typography.bodySmall
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            modifier = Modifier
                .clickable { onNavigateChangePassword() },
            textAlign = TextAlign.End,
            text = stringResource(R.string.login_screen_forget_password),
            color = MaterialTheme.colorScheme.onBackground,
            textDecoration = TextDecoration.Underline,
            style = Typography.bodySmall
        )
    }
}

@Preview
@Composable
private fun LoginHeaderPreview() {
    LoginBody(
        onNavigateChangePassword = {},
        email = TextFieldValue(""),
        emailValueChange = {},
        password = TextFieldValue(""),
        passwordValueChange = {},
        passwordVisible = false,
        passwordVisibleValueChange = {},
        emailFocusRequester = FocusRequester(),
        passwordFocusRequester = FocusRequester(),
    )
}
