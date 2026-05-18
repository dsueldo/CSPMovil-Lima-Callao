package com.colegiosociologosperu.cspmovillimacallao.presentation.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CreditCard
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Handshake
import androidx.compose.material.icons.filled.Newspaper
import kotlinx.serialization.Serializable

@Serializable data object Splash
@Serializable data object Login
@Serializable data object SignUp
@Serializable data object ChangePassword
@Serializable data object Main
@Serializable data object Profile
@Serializable data object EditProfile
@Serializable data object Benefit
@Serializable data object Payment
@Serializable data object PaymentInstructions
@Serializable data object NewsDetail

val topLevelRoutes = listOf(
    TopLevelRoute("Noticias", "news", Icons.Filled.Newspaper),
    TopLevelRoute("Convenios", "benefit", Icons.Filled.Handshake),
    TopLevelRoute("Pagos", "payment", Icons.Filled.CreditCard),
    TopLevelRoute("Perfil", "profile", Icons.Filled.Face),
)