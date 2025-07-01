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

@SuppressLint("RestrictedApi")
@Composable
fun ApplicationNavigation(
    splashViewModel: SplashViewModel = hiltViewModel()
) {

    var isAuthenticated by remember { mutableStateOf(splashViewModel.hasUser()) }

    if (isAuthenticated) {
        Log.d(
            stringResource(R.string.application_navigation),
            "MainNavigation.isAuthenticated: $isAuthenticated"
        )
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
    } else {
        AuthNavigation(onAuthComplete = { isAuthenticated = true })
        Log.d(
            stringResource(R.string.application_navigation),
            "isAuthenticated: $isAuthenticated"
        )
    }
}