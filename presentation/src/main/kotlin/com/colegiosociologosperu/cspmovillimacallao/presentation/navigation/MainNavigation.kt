package com.colegiosociologosperu.cspmovillimacallao.presentation.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.colegiosociologosperu.cspmovillimacallao.presentation.ui.benefits.BenefitsScreen
import com.colegiosociologosperu.cspmovillimacallao.presentation.ui.benefits.detail.BenefitsDetailScreen
import com.colegiosociologosperu.cspmovillimacallao.presentation.ui.contact.ContactScreen
import com.colegiosociologosperu.cspmovillimacallao.presentation.ui.editprofile.EditProfileScreen
import com.colegiosociologosperu.cspmovillimacallao.presentation.ui.news.NewsScreen
import com.colegiosociologosperu.cspmovillimacallao.presentation.ui.news.detail.NewsDetailScreen
import com.colegiosociologosperu.cspmovillimacallao.presentation.ui.news.favorites.FavoritesScreen
import com.colegiosociologosperu.cspmovillimacallao.presentation.ui.payment.PaymentOneScreen
import com.colegiosociologosperu.cspmovillimacallao.presentation.ui.payment.instruction.PaymentInstructionScreen
import com.colegiosociologosperu.cspmovillimacallao.presentation.ui.profile.ProfileScreen
import com.colegiosociologosperu.cspmovillimacallao.presentation.utils.theme.Red_Dark

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MainNavigation(
    onSignOut: () -> Unit,
    onDeleteAccount: () -> Unit,
) {
    val navController = rememberNavController()

    Scaffold(
        modifier = Modifier.systemBarsPadding(),
        bottomBar = {
            NavigationBar(
                containerColor = Red_Dark,
                contentColor = Color.White
            ) {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination
                topLevelRoutes.forEach { topLevelRoute ->
                    NavigationBarItem(
                        icon = {
                            Icon(
                                topLevelRoute.icon,
                                contentDescription = topLevelRoute.name
                            )
                        },
                        label = { Text(topLevelRoute.name) },
                        selected = currentDestination?.hierarchy?.any {
                            it.route == topLevelRoute.route
                        } == true,
                        onClick = {
                            navController.navigate(topLevelRoute.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = Color.White,
                            selectedTextColor = Color.White,
                            unselectedIconColor = Color.White.copy(alpha = 0.6f),
                            unselectedTextColor = Color.White.copy(alpha = 0.6f),
                            indicatorColor = Color.White.copy(alpha = 0.2f)
                        )
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "news",
            modifier = Modifier.padding(innerPadding)
        ) {
            composable("news") {
                NewsScreen(navController = navController)
            }
            composable("benefit") {
                BenefitsScreen(navController = navController)
            }
            composable("payment") { PaymentOneScreen(navController) }
            composable(
                "paymentInstruction/{title}",
                arguments = listOf(navArgument("title") { type = NavType.StringType })
            ) { backStackEntry ->
                val title = backStackEntry.arguments?.getString("title") ?: ""
                PaymentInstructionScreen(navController, title)
            }
            composable("profile") { ProfileScreen(
                onSignOut,
                onDeleteAccount,
                navController,
            ) }
            composable("contact") { ContactScreen(navController) }
            composable("editProfile") { EditProfileScreen(navController) }
            composable(
                "news/{newsId}",
                arguments = listOf(navArgument("newsId") { type = NavType.StringType })
            ) { backStackEntry ->
                val newsId = backStackEntry.arguments?.getString("newsId") ?: ""
                NewsDetailScreen(newsId, navController)
            }
            composable(
                "benefits/{benefitsId}",
                arguments = listOf(navArgument("benefitsId") { type = NavType.StringType })
            ) { backStackEntry ->
                val benefitsId = backStackEntry.arguments?.getString("benefitsId") ?: ""
                BenefitsDetailScreen(benefitsId, navController)
            }
            composable("favorites") {
                FavoritesScreen(navController = navController)
            }
        }
    }
}
