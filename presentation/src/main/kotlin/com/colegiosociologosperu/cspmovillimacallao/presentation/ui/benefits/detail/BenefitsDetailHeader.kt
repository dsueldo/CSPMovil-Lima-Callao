package com.colegiosociologosperu.cspmovillimacallao.presentation.ui.benefits.detail

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.colegiosociologosperu.cspmovillimacallao.presentation.ui.components.HeaderComponente

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BenefitsDetailHeader(
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {

    HeaderComponente(
        modifier = modifier,
        title = "Detalle del Convenio",
        onBack = onBack
    )
}