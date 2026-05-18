package com.colegiosociologosperu.cspmovillimacallao.presentation.ui.benefits

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.PullToRefreshDefaults
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.colegiosociologosperu.cspmovillimacallao.domain.entities.benefits.Benefits
import com.colegiosociologosperu.cspmovillimacallao.presentation.ui.components.BenefitsCard
import com.colegiosociologosperu.cspmovillimacallao.presentation.ui.components.BenefitsCardShimmer
import com.colegiosociologosperu.cspmovillimacallao.presentation.utils.theme.Red_Dark
import com.colegiosociologosperu.cspmovillimacallao.presentation.utils.theme.Typography
import com.colegiosociologosperu.cspmovillimacallao.presentation.viewmodels.benefits.BenefitsListViewModel

@Composable
fun BenefitsScreen(
    navController: NavHostController,
    viewModel: BenefitsListViewModel = hiltViewModel()
) {
    val benefitsList by viewModel.benefitsList.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val isRefreshing by viewModel.isRefreshing.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.refreshBenefitsList()
    }

    BenefitsScreenContent(
        benefitsList = benefitsList,
        isLoading = isLoading,
        isRefreshing = isRefreshing,
        onRefresh = { viewModel.refreshBenefitsList() },
        onBenefitsClick = { benefit ->
            navController.navigate("benefits/${benefit.id}")
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BenefitsScreenContent(
    benefitsList: List<Benefits>,
    isLoading: Boolean,
    isRefreshing: Boolean,
    onRefresh: () -> Unit,
    onBenefitsClick: (Benefits) -> Unit
) {
    val pullToRefreshState = rememberPullToRefreshState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Convenios",
                        style = Typography.headlineMedium,
                        fontWeight = FontWeight.Bold
                    )
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
                    if (isLoading && !isRefreshing && benefitsList.isEmpty()) {
                        items(5) {
                            BenefitsCardShimmer(
                                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                            )
                        }
                    } else {
                        items(
                            items = benefitsList,
                            key = { it.id }
                        ) { benefits ->
                            BenefitsCard(
                                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                                benefits = benefits,
                                onBenefitsClick = { onBenefitsClick(benefits) }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun BenefitsScreenPreview() {
    val mockBenefits = listOf(
        Benefits(id = "1", title = "Convenio 1", content = "Descripción 1"),
        Benefits(id = "2", title = "Convenio 2", content = "Descripción 2")
    )
    MaterialTheme {
        BenefitsScreenContent(
            benefitsList = mockBenefits,
            isLoading = false,
            isRefreshing = false,
            onRefresh = {},
            onBenefitsClick = {}
        )
    }
}
