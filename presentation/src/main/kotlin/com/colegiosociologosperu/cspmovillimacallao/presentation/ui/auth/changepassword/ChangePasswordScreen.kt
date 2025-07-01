package com.colegiosociologosperu.cspmovillimacallao.presentation.ui.auth.changepassword

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
import com.colegiosociologosperu.cspmovillimacallao.presentation.viewmodels.auth.changepassword.ChangePasswordViewModel
import com.colegiosociologosperu.cspmovillimacallao.presentation.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChangePasswordScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    changePasswordViewModel: ChangePasswordViewModel = hiltViewModel(),
) {
    val email by changePasswordViewModel.email.collectAsState()
    val confirmEmail by changePasswordViewModel.confirmEmail.collectAsState()

    val emailFocusRequester = remember { FocusRequester() }
    val confirmEmailFocusRequester = remember { FocusRequester() }

    val isLoading by changePasswordViewModel.isLoading.collectAsState()

    val isEmailSent by changePasswordViewModel.isEmailSent.collectAsState()

    var errorMessage by remember { mutableStateOf("") }

    var showDialog by remember { mutableStateOf(false) }
    var showErrorDialog by remember { mutableStateOf(false) }

    val emailsMatch = email.text == confirmEmail.text

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

    if (isEmailSent) {
        AlertDialog(
            onDismissRequest = {
                showDialog = false
                changePasswordViewModel.resetState()
            },
            confirmButton = {
                Button(
                    onClick = {
                        showDialog = false
                        changePasswordViewModel.resetState()
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
            title = { Text(stringResource(R.string.alert)) },
            text = { Text(stringResource(R.string.send_email)) }
        )
    }

    if (showErrorDialog) {
        AlertDialog(
            onDismissRequest = {
                showErrorDialog = false
                changePasswordViewModel.resetState()
            },
            confirmButton = {
                Button(
                    onClick = {
                        showErrorDialog = false
                        changePasswordViewModel.resetState()
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

    Scaffold(
        modifier = modifier
            .systemBarsPadding()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp),
        topBar = {
            ChangePasswordHeader(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                onBack = { navController.popBackStack() }
            )
        },
        content = { padding ->
            ChangePasswordBody(
                modifier = Modifier
                    .padding(padding)
                    .verticalScroll(rememberScrollState()),
                email = email,
                confirmEmail = confirmEmail,
                emailFocusRequester = emailFocusRequester,
                confirmEmailFocusRequester = confirmEmailFocusRequester,
                emailValueChange = { changePasswordViewModel.updateEmail(it) },
                confirmEmailValueChange = { changePasswordViewModel.updateConfirmEmail(it) },
            )
        },
        bottomBar = {
            ChangePasswordFooter(
                onClick = {
                    if (!emailsMatch) {
                        showErrorDialog = true
                        errorMessage = "Correos no coinciden."
                        return@ChangePasswordFooter
                    }

                    val isConfirmEmailValid = changePasswordViewModel.validateEnterConfirmEmail()
                    val isEmailValid = changePasswordViewModel.validateEnterEmail()
                    if (isEmailValid && isConfirmEmailValid) {
                        changePasswordViewModel.sendPasswordResetEmail()
                    } else {
                        showErrorDialog = true
                        errorMessage = changePasswordViewModel.errorMessage.value
                    }
              },
                modifier = Modifier.padding(top = 16.dp)
            )
        }
    )
}

@Preview
@Composable
private fun ChangePasswordScreenPreview() {
    ChangePasswordScreen(
        navController = rememberNavController()
    )
}