package com.colegiosociologosperu.cspmovillimacallao.presentation.ui.editprofile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.colegiosociologosperu.cspmovillimacallao.presentation.viewmodels.editprofile.EditProfileViewModel

@Composable
fun EditProfileScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    viewModel: EditProfileViewModel = hiltViewModel(),
) {

    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(uiState) {
        if (uiState) {
            navController.previousBackStackEntry
                ?.savedStateHandle
                ?.set("profile_updated", true)
            navController.popBackStack()
        }
    }

    Scaffold(
        modifier = modifier
            .systemBarsPadding()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp),
        topBar = {
            EditProfileHeader(
                onBack = { navController.popBackStack() },
                modifier = Modifier
            )
        },
        content = { paddingValues ->
            EditProfileBody(
                modifier = Modifier
                    .padding(paddingValues)
                    .verticalScroll(rememberScrollState()),
                viewModel = viewModel,
            )
        },
        bottomBar = {
            EditProfileFooter(
                modifier = Modifier,
                onClickSave = {
                    viewModel.saveProfile()
                }
            )
        }
    )
}