package com.colegiosociologosperu.cspmovillimacallao.presentation.ui.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.SwipeRefreshIndicator
import com.google.accompanist.swiperefresh.SwipeRefreshState
import com.colegiosociologosperu.cspmovillimacallao.presentation.utils.theme.Red_Dark
import com.colegiosociologosperu.cspmovillimacallao.presentation.viewmodels.profile.ProfileViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    onSignOut: () -> Unit,
    onDeleteAccount: () -> Unit,
    navController: NavController = rememberNavController(),
    modifier: Modifier = Modifier,
    viewModel: ProfileViewModel = hiltViewModel()
) {
    val profileState by viewModel.profileUiState.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()
    val showSignOutDialog = remember { mutableStateOf(false) }
    val showDeleteAccountDialog = remember { mutableStateOf(false) }
    val isRefreshing by viewModel.isRefreshing.collectAsState()
    val email = viewModel.userEmail.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.refreshProfile()
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

    if (errorMessage.isNotEmpty()) {
        Text(text = errorMessage, color = MaterialTheme.colorScheme.error)
    }

    if (showSignOutDialog.value) {
        AlertDialog(
            onDismissRequest = { showSignOutDialog.value = false },
            confirmButton = {
                Button(
                    onClick = {
                        showSignOutDialog.value = false
                        onSignOut()
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Red_Dark,
                        contentColor = Color.White
                    )
                ) {
                    Text("OK")
                }
            },
            title = { Text("Cerrar Sesión") },
            text = { Text("¿Está seguro que desea cerrar sesión?") },
            dismissButton = {
                Button(
                    onClick = { showSignOutDialog.value = false },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Red_Dark,
                        contentColor = Color.White
                    )
                ) {
                    Text("Cancelar")
                }
            }
        )
    }

    if (showDeleteAccountDialog.value) {
        AlertDialog(
            onDismissRequest = { showDeleteAccountDialog.value = false },
            confirmButton = {
                Button(
                    onClick = {
                        showDeleteAccountDialog.value = false
                        onDeleteAccount()
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Red_Dark,
                        contentColor = Color.White
                    )
                ) {
                    Text("OK")
                }
            },
            title = { Text("Eliminar Cuenta") },
            text = { Text("¿Está seguro que desea eliminar cuenta?") },
            dismissButton = {
                Button(
                    onClick = { showDeleteAccountDialog.value = false },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Red_Dark,
                        contentColor = Color.White
                    )
                ) {
                    Text("Cancelar")
                }
            }
        )
    }

    Scaffold(
        modifier = modifier
            .background(MaterialTheme.colorScheme.background)
            .fillMaxSize()
            .padding(16.dp),
        topBar = {
            ProfileHeader(
                name = profileState.name,
                onEditAccount = { navController.navigate("editProfile") }
            )
        },
        content = { paddingValues ->
            SwipeRefresh(
                state = SwipeRefreshState(isRefreshing),
                onRefresh = { viewModel.refreshProfile() },
                indicator = { state, trigger ->
                    SwipeRefreshIndicator(
                        state = state,
                        refreshTriggerDistance = trigger,
                        scale = true,
                        contentColor = Red_Dark,
                    )
                }
            ) {
                ProfileBody(
                    modifier = Modifier
                        .padding(paddingValues)
                        .verticalScroll(rememberScrollState()),
                    viewModel = viewModel,
                    email = email.value,
                )
            }
        },
        bottomBar = {
            ProfileFooter(
                onSignOut = {
                    showSignOutDialog.value = true
                },
                onDeleteAccount = {
                    showDeleteAccountDialog.value = true
                },
            )
        }
    )
}

@Preview
@Composable
private fun ProfileScreenPreview() {
    ProfileScreen(
        onSignOut = {},
        onDeleteAccount = {}
    )
}