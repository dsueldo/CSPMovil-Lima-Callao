package com.colegiosociologosperu.cspmovillimacallao.presentation.navigation

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.colegiosociologosperu.cspmovillimacallao.presentation.viewmodels.auth.splash.SplashViewModel
import androidx.navigation.compose.rememberNavController
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.colegiosociologosperu.cspmovillimacallao.presentation.ui.admin.AdminScreen
import com.colegiosociologosperu.cspmovillimacallao.presentation.utils.theme.Red_Dark
import com.colegiosociologosperu.cspmovillimacallao.presentation.R

@RequiresApi(Build.VERSION_CODES.O)
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
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = Color.White
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_logo),
                    contentDescription = stringResource(R.string.logo),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(32.dp)
                )
                CircularProgressIndicator(
                    color = Red_Dark,
                    modifier = Modifier.padding(top = 32.dp)
                )
            }
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