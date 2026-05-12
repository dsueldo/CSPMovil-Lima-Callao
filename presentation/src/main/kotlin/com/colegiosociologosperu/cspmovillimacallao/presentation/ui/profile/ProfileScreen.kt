package com.colegiosociologosperu.cspmovillimacallao.presentation.ui.profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ContactSupport
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.PullToRefreshDefaults
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.colegiosociologosperu.cspmovillimacallao.domain.entities.user.ProfileUiState
import com.colegiosociologosperu.cspmovillimacallao.presentation.utils.theme.Red_Dark
import com.colegiosociologosperu.cspmovillimacallao.presentation.utils.theme.Typography
import com.colegiosociologosperu.cspmovillimacallao.presentation.viewmodels.profile.ProfileViewModel

@Composable
fun ProfileScreen(
    onSignOut: () -> Unit,
    navController: NavController = rememberNavController(),
    viewModel: ProfileViewModel = hiltViewModel()
) {
    val profileState by viewModel.profileUiState.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val isRefreshing by viewModel.isRefreshing.collectAsState()
    val email by viewModel.userEmail.collectAsState()

    val profileUpdated = navController.currentBackStackEntry
        ?.savedStateHandle
        ?.getLiveData<Boolean>("profile_updated")
        ?.observeAsState(false)

    LaunchedEffect(profileUpdated?.value) {
        if (profileUpdated?.value == true) {
            viewModel.refreshProfile()
            navController.currentBackStackEntry
                ?.savedStateHandle
                ?.set("profile_updated", false)
        }
    }

    ProfileContent(
        profileState = profileState,
        isLoading = isLoading,
        isRefreshing = isRefreshing,
        email = email,
        onSignOut = onSignOut,
        onRefresh = { viewModel.refreshProfile() },
        onEditProfile = { navController.navigate("editProfile") },
        onContact = { navController.navigate("contact") }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileContent(
    profileState: ProfileUiState,
    isLoading: Boolean,
    isRefreshing: Boolean,
    email: String,
    onSignOut: () -> Unit,
    onRefresh: () -> Unit,
    onEditProfile: () -> Unit,
    onContact: () -> Unit
) {
    var showSignOutDialog by remember { mutableStateOf(false) }

    if (showSignOutDialog) {
        AlertDialog(
            onDismissRequest = { showSignOutDialog = false },
            confirmButton = {
                Button(
                    onClick = {
                        showSignOutDialog = false
                        onSignOut()
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Red_Dark,
                        contentColor = Color.White
                    )
                ) {
                    Text("Cerrar Sesión")
                }
            },
            dismissButton = {
                Button(
                    onClick = { showSignOutDialog = false },
                    colors = ButtonDefaults.textButtonColors(contentColor = Red_Dark)
                ) {
                    Text("Cancelar")
                }
            },
            title = { Text("Cerrar Sesión") },
            text = { Text("¿Está seguro que desea cerrar sesión?") }
        )
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            text = "Perfil",
                            style = Typography.headlineMedium,
                            fontWeight = FontWeight.Bold
                        )
                    },
                    actions = {
                        IconButton(onClick = onContact) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ContactSupport,
                                contentDescription = "Contacto",
                                tint = Red_Dark
                            )
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.background,
                        titleContentColor = MaterialTheme.colorScheme.onBackground
                    )
                )
            },
            bottomBar = {
                ProfileFooter(
                    onSignOut = { showSignOutDialog = true }
                )
            }
        ) { paddingValues ->
            PullToRefreshBox(
                isRefreshing = isRefreshing,
                onRefresh = onRefresh,
                indicator = {
                    PullToRefreshDefaults.Indicator(
                        state = rememberPullToRefreshState(),
                        isRefreshing = isRefreshing,
                        containerColor = Color.White,
                        color = Red_Dark,
                        modifier = Modifier.align(Alignment.TopCenter)
                    )
                },
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp)
                        .verticalScroll(rememberScrollState())
                ) {
                    if (isLoading && !isRefreshing) {
                        Box(modifier = Modifier.padding(vertical = 16.dp)) {
                            ProfileHeaderShimmer()
                        }
                        Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                            HorizontalDivider()
                            repeat(8) {
                                ItemProfileShimmer()
                            }
                            HorizontalDivider()
                        }
                    } else {
                        Box(modifier = Modifier.padding(vertical = 16.dp)) {
                            ProfileHeader(
                                name = profileState.name,
                                onEditAccount = onEditProfile
                            )
                        }
                        ProfileBody(
                            profileUiState = profileState,
                            email = email,
                        )
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ProfileScreenPreview() {
    MaterialTheme {
        ProfileContent(
            profileState = ProfileUiState(
                name = "Juan",
                lastName = "Perez",
                phone = "987654321",
                dni = "12345678",
                gender = "Masculino",
                birthday = "01/01/1990",
                codeNumber = "12345",
                condition = "Activo",
                specialized = "Sociología",
                payLastPeriod = "2023-12"
            ),
            isLoading = false,
            isRefreshing = false,
            email = "juan.perez@example.com",
            onSignOut = {},
            onRefresh = {},
            onEditProfile = {},
            onContact = {}
        )
    }
}
