package com.colegiosociologosperu.cspmovillimacallao.presentation.ui.admin

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.PullToRefreshDefaults
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.colegiosociologosperu.cspmovillimacallao.domain.entities.user.ProfileUiState
import com.colegiosociologosperu.cspmovillimacallao.presentation.ui.admin.components.AdminBody
import com.colegiosociologosperu.cspmovillimacallao.presentation.ui.admin.components.AdminDrawerContent
import com.colegiosociologosperu.cspmovillimacallao.presentation.ui.admin.components.AdminHeader
import com.colegiosociologosperu.cspmovillimacallao.presentation.utils.theme.Red_Dark
import com.colegiosociologosperu.cspmovillimacallao.presentation.viewmodels.admin.AdminViewModel
import kotlinx.coroutines.launch

@Composable
fun AdminScreen(
    navController: NavController,
    onSignOut: () -> Unit,
    viewModel: AdminViewModel = hiltViewModel()
) {
    val users by viewModel.filteredUsers.collectAsState(initial = emptyList())
    val adminProfile by viewModel.adminProfile.collectAsState()
    val searchQuery by viewModel.searchQuery.collectAsState()
    val isLoading by viewModel.loading.collectAsState()
    val isRefreshing by viewModel.isRefreshing.collectAsState()
    
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    var showLogoutDialog by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        viewModel.loadData()
    }

    if (showLogoutDialog) {
        AlertDialog(
            onDismissRequest = { showLogoutDialog = false },
            title = { Text(text = "Cerrar sesión") },
            text = { Text(text = "¿Estás seguro que deseas cerrar sesión?") },
            confirmButton = {
                Button(
                    onClick = {
                        showLogoutDialog = false
                        onSignOut()
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Red_Dark)
                ) {
                    Text("Confirmar", color = Color.White)
                }
            },
            dismissButton = {
                TextButton(onClick = { showLogoutDialog = false }) {
                    Text("Cancelar", color = Red_Dark)
                }
            }
        )
    }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            AdminDrawerContent(
                admin = adminProfile,
                selectedRoute = "admin",
                onNavigateToAdmin = {
                    scope.launch { drawerState.close() }
                },
                onNavigateToNews = {
                    scope.launch { 
                        drawerState.close()
                        navController.navigate("main") {
                            launchSingleTop = true
                        }
                    }
                }
            )
        }
    ) {
        AdminScreenContent(
            users = users,
            searchQuery = searchQuery,
            isLoading = isLoading,
            isRefreshing = isRefreshing,
            onRefresh = { viewModel.loadData(isRefresh = true) },
            onOpenDrawer = { scope.launch { drawerState.open() } },
            onSignOut = { showLogoutDialog = true },
            onSearchQueryChange = { viewModel.onSearchQueryChange(it) },
            onUpdateUser = { uid, spec, cond, pay ->
                viewModel.updateProfessionalInfo(uid, spec, cond, pay)
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminScreenContent(
    users: List<ProfileUiState>,
    searchQuery: String,
    isLoading: Boolean,
    isRefreshing: Boolean,
    onRefresh: () -> Unit,
    onOpenDrawer: () -> Unit,
    onSignOut: () -> Unit,
    onSearchQueryChange: (String) -> Unit,
    onUpdateUser: (uid: String, specialized: String, condition: String, payLastPeriod: String) -> Unit,
) {
    val pullToRefreshState = rememberPullToRefreshState()
    
    Scaffold(
        topBar = {
            AdminHeader(
                onOpenDrawer = onOpenDrawer,
                onSignOut = onSignOut
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            PullToRefreshBox(
                isRefreshing = isRefreshing,
                onRefresh = onRefresh,
                state = pullToRefreshState,
                indicator = {
                    PullToRefreshDefaults.Indicator(
                        state = pullToRefreshState,
                        isRefreshing = isRefreshing,
                        containerColor = Color.White,
                        color = Red_Dark,
                        modifier = Modifier.align(Alignment.TopCenter)
                    )
                },
                modifier = Modifier.fillMaxSize()
            ) {
                AdminBody(
                    users = users,
                    searchQuery = searchQuery,
                    isLoading = isLoading,
                    onSearchQueryChange = onSearchQueryChange,
                    onUpdateUser = onUpdateUser
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AdminScreenPreview() {
    AdminScreenContent(
        users = listOf(
            ProfileUiState(name = "Christian", lastName = "Quispe", dni = "12345678"),
            ProfileUiState(name = "Juan", lastName = "Perez", dni = "87654321")
        ),
        searchQuery = "",
        isLoading = false,
        isRefreshing = false,
        onRefresh = {},
        onOpenDrawer = {},
        onSignOut = {},
        onSearchQueryChange = {},
        onUpdateUser = { _, _, _, _ -> }
    )
}