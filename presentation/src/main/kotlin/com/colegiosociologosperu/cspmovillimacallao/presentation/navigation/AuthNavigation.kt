package com.colegiosociologosperu.cspmovillimacallao.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.colegiosociologosperu.cspmovillimacallao.presentation.ui.auth.changepassword.ChangePasswordScreen
import com.colegiosociologosperu.cspmovillimacallao.presentation.ui.auth.login.LoginScreen
import com.colegiosociologosperu.cspmovillimacallao.presentation.ui.auth.signUp.SignUpScreen
import com.colegiosociologosperu.cspmovillimacallao.presentation.ui.auth.splash.SplashScreen

@Composable
fun AuthNavigation(onAuthComplete: () -> Unit) {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Splash
    ) {
        composable<Splash> { SplashScreen( navController ) }
        composable<Login> {
            LoginScreen(
                navController = navController,
                onAuthComplete = onAuthComplete
            )
        }
        composable<SignUp> { SignUpScreen( navController) }
        composable<ChangePassword> { ChangePasswordScreen(navController) }
    }
}
