package com.colegiosociologosperu.cspmovillimacallao.presentation.ui.news

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.PullToRefreshDefaults
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.colegiosociologosperu.cspmovillimacallao.domain.entities.news.News
import com.colegiosociologosperu.cspmovillimacallao.presentation.ui.admin.components.AdminDrawerContent
import com.colegiosociologosperu.cspmovillimacallao.presentation.ui.components.NewsCard
import com.colegiosociologosperu.cspmovillimacallao.presentation.ui.components.NewsCardShimmer
import com.colegiosociologosperu.cspmovillimacallao.presentation.utils.theme.Red_Dark
import com.colegiosociologosperu.cspmovillimacallao.presentation.utils.theme.Typography
import com.colegiosociologosperu.cspmovillimacallao.presentation.viewmodels.news.NewsListViewModel
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NewsScreen(
    navController: NavHostController,
    onNavigateToAdmin: () -> Unit = {},
    viewModel: NewsListViewModel = hiltViewModel(),
) {
    val newsList by viewModel.newsList.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val isRefreshing by viewModel.isRefreshing.collectAsState()
    val adminProfile by viewModel.adminProfile.collectAsState()

    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        viewModel.refreshNewsList()
    }

    if (adminProfile?.role == "admin") {
        ModalNavigationDrawer(
            drawerState = drawerState,
            drawerContent = {
                AdminDrawerContent(
                    admin = adminProfile,
                    selectedRoute = "news",
                    onNavigateToAdmin = {
                        scope.launch {
                            drawerState.close()
                            onNavigateToAdmin()
                        }
                    },
                    onNavigateToNews = {
                        scope.launch { drawerState.close() }
                    }
                )
            }
        ) {
            NewsScreenContent(
                newsList = newsList,
                isLoading = isLoading,
                isRefreshing = isRefreshing,
                isAdmin = true,
                onRefresh = { viewModel.refreshNewsList() },
                onOpenDrawer = { scope.launch { drawerState.open() } },
                onNewsClick = { news ->
                    navController.navigate("news/${news.id}")
                },
                onFavoritesClick = {
                    navController.navigate("favorites")
                },
                onAddNewsClick = {
                    navController.navigate("addNews")
                }
            )
        }
    } else {
        NewsScreenContent(
            newsList = newsList,
            isLoading = isLoading,
            isRefreshing = isRefreshing,
            isAdmin = false,
            onRefresh = { viewModel.refreshNewsList() },
            onOpenDrawer = {},
            onNewsClick = { news ->
                navController.navigate("news/${news.id}")
            },
            onFavoritesClick = {
                navController.navigate("favorites")
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NewsScreenContent(
    newsList: List<News>,
    isLoading: Boolean,
    isRefreshing: Boolean,
    isAdmin: Boolean,
    onRefresh: () -> Unit,
    onOpenDrawer: () -> Unit,
    onNewsClick: (News) -> Unit,
    onFavoritesClick: () -> Unit,
    onAddNewsClick: () -> Unit = {}
) {
    val pullToRefreshState = rememberPullToRefreshState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Noticias",
                        style = Typography.headlineMedium,
                        fontWeight = FontWeight.Bold
                    )
                },
                navigationIcon = {
                    if (isAdmin) {
                        IconButton(onClick = onOpenDrawer) {
                            Icon(
                                imageVector = Icons.Default.Menu,
                                contentDescription = "Menu"
                            )
                        }
                    }
                },
                actions = {
                    if (isAdmin) {
                        IconButton(onClick = onAddNewsClick) {
                            Icon(
                                imageVector = Icons.Default.Add,
                                contentDescription = "Agregar Noticia",
                                tint = Red_Dark
                            )
                        }
                    }
                    IconButton(onClick = onFavoritesClick) {
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = "Favoritos",
                            tint = Red_Dark
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background,
                    titleContentColor = MaterialTheme.colorScheme.onBackground
                )
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            PullToRefreshBox(
                isRefreshing = isRefreshing,
                onRefresh = onRefresh,
                state = pullToRefreshState,
                indicator = {
                    PullToRefreshDefaults.Indicator(
                        state = pullToRefreshState,
                        isRefreshing = isRefreshing,
                        containerColor = Color.White,
                        color = Red_Dark,
                        modifier = Modifier.align(Alignment.TopCenter)
                    )
                },
                modifier = Modifier.fillMaxSize()
            ) {
                LazyColumn(
                    modifier = Modifier
                        .background(MaterialTheme.colorScheme.background)
                        .fillMaxSize()
                ) {
                    if (isLoading && !isRefreshing && newsList.isEmpty()) {
                        items(5) {
                            NewsCardShimmer(
                                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                            )
                        }
                    } else {
                        items(
                            items = newsList,
                            key = { it.id }
                        ) { news ->
                            NewsCard(
                                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                                news = news,
                                onNewsClick = onNewsClick
                            )
                        }
                    }
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun NewsScreenPreview() {
    val mockNews = listOf(
        News(
            id = "1",
            title = "Nueva app lanzada",
            description = "Se ha lanzado la nueva aplicación del Colegio de Sociólogos del Perú.",
            date = "2024-10-27T10:00:00Z",
            source = "CSP"
        ),
        News(
            id = "2",
            title = "Evento anual",
            description = "Invitamos a todos los colegiados al evento anual de integración.",
            date = "2024-10-26T09:00:00Z",
            source = "CSP"
        )
    )
    MaterialTheme {
        NewsScreenContent(
            newsList = mockNews,
            isLoading = false,
            isRefreshing = false,
            isAdmin = true,
            onRefresh = {},
            onOpenDrawer = {},
            onNewsClick = {},
            onFavoritesClick = {}
        )
    }
}
