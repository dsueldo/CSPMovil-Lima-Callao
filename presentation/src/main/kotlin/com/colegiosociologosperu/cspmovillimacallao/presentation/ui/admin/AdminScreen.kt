package com.colegiosociologosperu.cspmovillimacallao.presentation.ui.admin

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
    
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

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
            onOpenDrawer = { scope.launch { drawerState.open() } },
            onSignOut = onSignOut,
            onSearchQueryChange = { viewModel.onSearchQueryChange(it) },
            onUpdateUser = { uid, spec, cond, pay ->
                viewModel.updateProfessionalInfo(uid, spec, cond, pay)
            }
        )
    }
}

@Composable
fun AdminScreenContent(
    users: List<ProfileUiState>,
    searchQuery: String,
    isLoading: Boolean,
    onOpenDrawer: () -> Unit,
    onSignOut: () -> Unit,
    onSearchQueryChange: (String) -> Unit,
    onUpdateUser: (uid: String, specialized: String, condition: String, payLastPeriod: String) -> Unit,
) {
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
            AdminBody(
                users = users,
                searchQuery = searchQuery,
                onSearchQueryChange = onSearchQueryChange,
                onUpdateUser = onUpdateUser
            )

            if (isLoading) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(color = Red_Dark)
                }
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
        onOpenDrawer = {},
        onSignOut = {},
        onSearchQueryChange = {},
        onUpdateUser = { _, _, _, _ -> }
    )
}