package com.colegiosociologosperu.cspmovillimacallao.presentation.ui.contact

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun ContactScreen(navController: NavController) {
    Scaffold(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background)
            .fillMaxSize()
            .padding(16.dp),
        topBar = {
            ContactHeader(
                onBack = { navController.popBackStack() },
                modifier = Modifier
            )
        },
        content = { paddingValues ->
            ContactBody(
                modifier = Modifier
                    .padding(paddingValues),
            )
        },
    )
}