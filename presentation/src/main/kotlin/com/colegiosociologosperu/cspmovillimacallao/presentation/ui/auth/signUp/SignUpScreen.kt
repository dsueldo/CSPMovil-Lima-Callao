package com.colegiosociologosperu.cspmovillimacallao.presentation.ui.auth.signUp

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.colegiosociologosperu.cspmovillimacallao.presentation.navigation.Login
import com.colegiosociologosperu.cspmovillimacallao.presentation.utils.theme.Red_Dark
import com.colegiosociologosperu.cspmovillimacallao.presentation.viewmodels.auth.signUp.SignUpViewModel
import com.colegiosociologosperu.cspmovillimacallao.presentation.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignUpScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    signUpViewModel: SignUpViewModel = hiltViewModel(),
) {
    val email by signUpViewModel.email.collectAsState()
    val password by signUpViewModel.password.collectAsState()
    val confirmPassword by signUpViewModel.confirmPassword.collectAsState()

    var passwordVisible by remember { mutableStateOf(false) }
    var confirmPasswordVisible by remember { mutableStateOf(false) }

    var passwordStrength by remember { mutableStateOf(signUpViewModel.validatePasswordStrength(password.toString())) }
    var showPasswordStrength by remember { mutableStateOf(false) }

    val emailFocusRequester = remember { FocusRequester() }
    val passwordFocusRequester = remember { FocusRequester() }
    val confirmPasswordFocusRequester = remember { FocusRequester() }

    val isLoading by signUpViewModel.isLoading.collectAsState()
    val uiState by signUpViewModel.uiState.collectAsState()

    var errorMessage by remember { mutableStateOf("") }
    val errorAccountValidationMessage by signUpViewModel.errorMessage.collectAsState()

    var showDialog by remember { mutableStateOf(false) }
    var showErrorDialog by remember { mutableStateOf(false) }

    val passwordsMatch = password.text == confirmPassword.text

    if (uiState) {
        showDialog = true
    }

    if (isLoading) {
        BasicAlertDialog(
            onDismissRequest = { },
            content = {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    CircularProgressIndicator(
                        color = Red_Dark
                    )
                }
            },
        )
    }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = {
                showDialog = false
                signUpViewModel.resetState()
            },
            confirmButton = {
                Button(
                    onClick = {
                        showDialog = false
                        signUpViewModel.resetState()
                        navController.navigate(Login)
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Red_Dark,
                        contentColor = Color.White
                    )
                ) {
                    Text(stringResource(R.string.ok))
                }
            },
            title = { Text(stringResource(R.string.register_successful)) },
            text = { Text(stringResource(R.string.register_successful_detail)) }
        )
    }

    if (showErrorDialog) {
        AlertDialog(
            onDismissRequest = {
                showErrorDialog = false
                signUpViewModel.resetState()
            },
            confirmButton = {
                Button(
                    onClick = {
                        showErrorDialog = false
                        signUpViewModel.resetState()
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Red_Dark,
                        contentColor = Color.White
                    )
                ) {
                    Text(stringResource(R.string.ok))
                }
            },
            title = { Text(stringResource(R.string.alert)) },
            text = { Text(errorMessage) }
        )
    }

    if (errorAccountValidationMessage == stringResource(R.string.register_email_error)) {
        AlertDialog(
            onDismissRequest = {
                signUpViewModel.resetState()
            },
            confirmButton = {
                Button(onClick = {
                    signUpViewModel.resetState()
                },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Red_Dark,
                        contentColor = Color.White
                    )
                ) {
                    Text(stringResource(R.string.ok))
                }
            },
            title = { Text(stringResource(R.string.register_successful_account)) },
            text = { Text(stringResource(R.string.register_email_error)) }
        )
    }

    if (errorAccountValidationMessage == stringResource(R.string.register_email_error_not_exist)) {
        AlertDialog(
            onDismissRequest = {
                signUpViewModel.resetState()
            },
            confirmButton = {
                Button(onClick = {
                    signUpViewModel.resetState()
                },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Red_Dark,
                        contentColor = Color.White
                    )
                ) {
                    Text(stringResource(R.string.ok))
                }
            },
            title = { Text(stringResource(R.string.alert)) },
            text = { Text(stringResource(R.string.register_email_error_not_exist)) }
        )
    }

    Scaffold(
        modifier = modifier
            .systemBarsPadding()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp),
        topBar = {
            SignUpHeader(
                modifier = Modifier.padding(bottom = 16.dp)
            )
        },
        content = { padding ->
            SignUpBody(
                modifier = Modifier
                    .padding(padding)
                    .verticalScroll(rememberScrollState()),

                email = email,
                password = password,
                confirmPassword = confirmPassword,

                passwordVisible = passwordVisible,
                confirmPasswordVisible = confirmPasswordVisible,

                passwordStrength = passwordStrength,
                showPasswordStrength = showPasswordStrength,

                emailFocusRequester = emailFocusRequester,
                passwordFocusRequester = passwordFocusRequester,
                confirmPasswordFocusRequester = confirmPasswordFocusRequester,

                emailValueChange = { signUpViewModel.updateEmail(it) },
                passwordValueChange = {
                    signUpViewModel.updatePassword(it)
                    passwordStrength = signUpViewModel.validatePasswordStrength(it.text)
                    showPasswordStrength = true
                },
                confirmPasswordValueChange = { signUpViewModel.updateConfirmPassword(it) },

                passwordVisibleValueChange = { passwordVisible = it },
                confirmPasswordVisibleValueChange = { confirmPasswordVisible = it },
            )
        },
        bottomBar = {
            SignUpFooter(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(16.dp, 0.dp),
                onClickRegistration = {
                    if (!passwordsMatch) {
                     showErrorDialog = true
                     errorMessage = "Contraseñas no coinciden."
                     return@SignUpFooter
                    }

                    val isPasswordValid = signUpViewModel.validateEnterPassword()
                    val isConfirmPasswordValid = signUpViewModel.validateEnterConfirmPassword()
                    val isEmailValid = signUpViewModel.validateEnterEmail()
                    if (isEmailValid && isPasswordValid && isConfirmPasswordValid) {
                        if(!signUpViewModel.validatePasswordComplexity(password.text)) {
                            showErrorDialog = true
                            errorMessage = "Contraseña no cumple con los requisitos."
                        } else {
                            signUpViewModel.onSignUpClick()
                        }
                    } else {
                        showErrorDialog = true
                        errorMessage = signUpViewModel.errorMessage.value
                    }
                    passwordStrength = signUpViewModel.validatePasswordStrength(password.text)
                    showPasswordStrength = true
                },
            )
        }
    )
}

@Preview
@Composable
private fun SignUpScreenPreview() {
    SignUpScreen(
        navController = rememberNavController()
    )
}