package com.colegiosociologosperu.cspmovillimacallao.presentation.navigation

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material3.Scaffold
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
import com.colegiosociologosperu.cspmovillimacallao.data.repositories.BenefitsRepositoryImpl
import com.colegiosociologosperu.cspmovillimacallao.data.repositories.NewsRepositoryImpl
import com.colegiosociologosperu.cspmovillimacallao.presentation.ui.benefits.BenefitsScreen
import com.colegiosociologosperu.cspmovillimacallao.presentation.ui.benefits.detail.BenefitsDetailScreen
import com.colegiosociologosperu.cspmovillimacallao.presentation.ui.contact.ContactScreen
import com.colegiosociologosperu.cspmovillimacallao.presentation.ui.editprofile.EditProfileScreen
import com.colegiosociologosperu.cspmovillimacallao.presentation.ui.news.NewsScreen
import com.colegiosociologosperu.cspmovillimacallao.presentation.ui.news.detail.NewsDetailScreen
import com.colegiosociologosperu.cspmovillimacallao.presentation.ui.payment.PaymentOneScreen
import com.colegiosociologosperu.cspmovillimacallao.presentation.ui.payment.instruction.PaymentInstructionScreen
import com.colegiosociologosperu.cspmovillimacallao.presentation.ui.profile.ProfileScreen
import com.colegiosociologosperu.cspmovillimacallao.presentation.utils.theme.Red_Dark

@SuppressLint("RestrictedApi")
@Composable
fun MainNavigation(
    onSignOut: () -> Unit,
    onDeleteAccount: () -> Unit,
) {

    val newsRepository = NewsRepositoryImpl()
    val benefitsRepository = BenefitsRepositoryImpl()
    val navController = rememberNavController()

    Scaffold(
        modifier = Modifier.systemBarsPadding(),
        bottomBar = {
            BottomNavigation(
                backgroundColor = Red_Dark,
                contentColor = Color.White
            ) {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination
                topLevelRoutes.forEach { topLevelRoute ->
                    BottomNavigationItem(
                        icon = {
                            Icon(
                                topLevelRoute.icon,
                                contentDescription = topLevelRoute.name
                            )
                        },
                        label = { Text(topLevelRoute.name) },
                        selected = currentDestination?.hierarchy?.any {
                            it.hasRoute(topLevelRoute.route.toString(), arguments = null)
                        } == true,
                        onClick = {
                            navController.navigate(topLevelRoute.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
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
                NewsScreen(navController = navController, newsRepository = newsRepository)
            }
            composable("benefit") {
                BenefitsScreen(navController = navController, benefitsRepository = benefitsRepository)
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
        }
    }
}