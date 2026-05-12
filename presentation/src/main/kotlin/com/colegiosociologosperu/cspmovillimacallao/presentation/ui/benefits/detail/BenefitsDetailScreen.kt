package com.colegiosociologosperu.cspmovillimacallao.presentation.ui.benefits.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.colegiosociologosperu.cspmovillimacallao.domain.entities.benefits.Benefits
import com.colegiosociologosperu.cspmovillimacallao.presentation.ui.components.ZoomableAsyncImage
import com.colegiosociologosperu.cspmovillimacallao.presentation.utils.theme.Red_Dark
import com.colegiosociologosperu.cspmovillimacallao.presentation.viewmodels.benefits.detail.BenefitsDetailViewModel

@Composable
fun BenefitsDetailScreen(
    benefitsId: String,
    navController: NavController,
    modifier: Modifier = Modifier,
    viewModel: BenefitsDetailViewModel = hiltViewModel(),
) {
    val benefitsDetail by viewModel.benefitsDetail.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()

    LaunchedEffect(benefitsId) {
        viewModel.fetchBenefitsDetail(benefitsId)
    }

    BenefitsDetailContent(
        benefitsDetail = benefitsDetail,
        isLoading = isLoading,
        errorMessage = errorMessage,
        onBack = { navController.popBackStack() },
        modifier = modifier
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BenefitsDetailContent(
    benefitsDetail: Benefits,
    isLoading: Boolean,
    errorMessage: String,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    var fullscreenImageState by remember { mutableStateOf<String?>(null) }

    Box(modifier = Modifier.fillMaxSize()) {
        Scaffold(
            modifier = modifier,
            topBar = {
                BenefitsDetailHeader(
                    onBack = onBack
                )
            }
        ) { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .background(MaterialTheme.colorScheme.background)
            ) {
                if (benefitsDetail.id.isNotEmpty()) {
                    BenefitsDetailBody(
                        benefitsDetail = benefitsDetail,
                        onImageClick = { imageUrl ->
                            fullscreenImageState = imageUrl
                        }
                    )
                }

                if (isLoading) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color.Black.copy(alpha = 0.12f)),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(color = Red_Dark)
                    }
                }

                if (errorMessage.isNotEmpty()) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = errorMessage,
                            color = MaterialTheme.colorScheme.error,
                            modifier = Modifier.padding(16.dp)
                        )
                    }
                }
            }
        }

        fullscreenImageState?.let { imageUrl ->
            Dialog(
                onDismissRequest = { fullscreenImageState = null },
                properties = DialogProperties(
                    usePlatformDefaultWidth = false,
                    decorFitsSystemWindows = false
                )
            ) {
                ZoomableAsyncImage(
                    imageUrl = imageUrl,
                    contentScale = ContentScale.Fit,
                    onClose = { fullscreenImageState = null },
                    backgroundColor = Color.Black,
                    closeIconColor = Color.White
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun BenefitsDetailScreenPreview() {
    val mockBenefits = Benefits(
        id = "1",
        title = "Convenio con Gimnasio",
        content = "Disfruta de un 20% de descuento en tu membresía mensual.",
        date = "2024-10-27",
        source = "CSP"
    )
    MaterialTheme {
        BenefitsDetailContent(
            benefitsDetail = mockBenefits,
            isLoading = false,
            errorMessage = "",
            onBack = {}
        )
    }
}
