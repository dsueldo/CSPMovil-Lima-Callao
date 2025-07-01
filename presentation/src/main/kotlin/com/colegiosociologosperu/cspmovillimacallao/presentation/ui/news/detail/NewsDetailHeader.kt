package com.colegiosociologosperu.cspmovillimacallao.presentation.ui.news.detail

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.colegiosociologosperu.cspmovillimacallao.presentation.ui.components.HeaderComponente

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewsDetailHeader(
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {

    HeaderComponente(
        modifier = modifier,
        title = "Detalle de la noticia",
        onBack = onBack
    )
}

@Preview
@Composable
private fun EditProfileHeaderPreview() {
    NewsDetailHeader(
        onBack = {}
    )
}