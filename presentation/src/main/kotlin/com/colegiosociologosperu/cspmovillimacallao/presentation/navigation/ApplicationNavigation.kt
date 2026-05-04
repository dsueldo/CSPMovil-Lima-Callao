package com.colegiosociologosperu.cspmovillimacallao.presentation.navigation

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.colegiosociologosperu.cspmovillimacallao.presentation.viewmodels.auth.splash.SplashViewModel
import com.colegiosociologosperu.cspmovillimacallao.presentation.R

import androidx.navigation.compose.rememberNavController
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import com.colegiosociologosperu.cspmovillimacallao.presentation.ui.admin.AdminScreen

@SuppressLint("RestrictedApi")
@Composable
fun ApplicationNavigation(
    splashViewModel: SplashViewModel = hiltViewModel()
) {
    val userRole by splashViewModel.userRole
    val isLoading by splashViewModel.isLoading
    var isAuthenticated by remember { mutableStateOf(splashViewModel.hasUser()) }

    LaunchedEffect(isAuthenticated) {
        if (isAuthenticated) {
            splashViewModel.checkUserStatus()
        }
    }

    if (isLoading && isAuthenticated) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    } else if (isAuthenticated) {
        if (userRole == "admin") {
            AdminScreen(
                navController = rememberNavController(),
                onSignOut = {
                    splashViewModel.signOut()
                    isAuthenticated = false
                }
            )
        } else {
            MainNavigation(
                onSignOut = {
                    splashViewModel.signOut()
                    isAuthenticated = false
                },
                onDeleteAccount = {
                    splashViewModel.deleteAccount()
                    isAuthenticated = false
                }
            )
        }
    } else {
        AuthNavigation(onAuthComplete = { 
            isAuthenticated = true 
        })
    }
}

