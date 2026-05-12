package com.colegiosociologosperu.cspmovillimacallao.presentation.ui.payment

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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.colegiosociologosperu.cspmovillimacallao.presentation.ui.components.ItemPaymentComponent
import com.colegiosociologosperu.cspmovillimacallao.presentation.ui.components.ItemPaymentShimmer
import com.colegiosociologosperu.cspmovillimacallao.presentation.utils.theme.Red_Dark
import com.colegiosociologosperu.cspmovillimacallao.presentation.utils.theme.Typography
import com.colegiosociologosperu.cspmovillimacallao.presentation.viewmodels.payment.PaymentsItem
import com.colegiosociologosperu.cspmovillimacallao.presentation.viewmodels.payment.PaymentsViewModel

@Composable
fun PaymentOneScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    viewModel: PaymentsViewModel = hiltViewModel(),
) {
    val paymentsList by viewModel.paymentsList.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val isRefreshing by viewModel.isRefreshing.collectAsState()

    PaymentOneContent(
        paymentsList = paymentsList,
        isLoading = isLoading,
        isRefreshing = isRefreshing,
        onRefresh = { viewModel.refreshPaymentsList() },
        onPaymentClick = { payment ->
            navController.navigate("paymentInstruction/${payment.title}")
        },
        modifier = modifier
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PaymentOneContent(
    paymentsList: List<PaymentsItem>,
    isLoading: Boolean,
    isRefreshing: Boolean,
    onRefresh: () -> Unit,
    onPaymentClick: (PaymentsItem) -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Pagos",
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
                indicator = {
                    PullToRefreshDefaults.Indicator(
                        state = rememberPullToRefreshState(),
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
                        .padding(horizontal = 16.dp)
                ) {
                    if (isLoading && !isRefreshing) {
                        items(8) {
                            ItemPaymentShimmer()
                        }
                    } else {
                        items(paymentsList) { paymentsItem ->
                            ItemPaymentComponent(
                                image = paymentsItem.image,
                                title = paymentsItem.title,
                                content = paymentsItem.content,
                                note = paymentsItem.note,
                                onClick = { onPaymentClick(paymentsItem) }
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
private fun PaymentOneScreenPreview() {
    val mockPayments = listOf(
        PaymentsItem(title = "Pago 1", content = "Contenido 1", note = "Nota 1"),
        PaymentsItem(title = "Pago 2", content = "Contenido 2", note = "Nota 2")
    )
    MaterialTheme {
        PaymentOneContent(
            paymentsList = mockPayments,
            isLoading = false,
            isRefreshing = false,
            onRefresh = {},
            onPaymentClick = {}
        )
    }
}
