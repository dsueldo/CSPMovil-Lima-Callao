package com.colegiosociologosperu.cspmovillimacallao.presentation.ui.benefits.detail

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.colegiosociologosperu.cspmovillimacallao.presentation.utils.theme.Typography
import com.colegiosociologosperu.cspmovillimacallao.presentation.viewmodels.benefits.detail.BenefitsDetailViewModel

@Composable
fun BenefitsDetailBody(
    modifier: Modifier = Modifier,
    viewModel: BenefitsDetailViewModel,
    onImageClick: (String) -> Unit
) {
    val benefitsDetail by viewModel.benefitsDetail.collectAsState()

    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AsyncImage(
            modifier = modifier
                .fillMaxWidth()
                .fillMaxSize()
                .clickable { onImageClick(benefitsDetail.image) },
            model = benefitsDetail.image,
            contentScale = ContentScale.Crop,
            contentDescription = null,
        )
        Spacer(modifier = Modifier.height(2.dp))
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = "Fuente: ${benefitsDetail.source}",
            style = Typography.bodySmall,
            color = Color.Black,
            textAlign = TextAlign.Start,
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = benefitsDetail.date,
            style = Typography.bodyMedium,
            color = Color.Black,
            textAlign = TextAlign.Start,
        )
        Spacer(modifier = Modifier.padding(8.dp))
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = benefitsDetail.title,
            fontWeight = FontWeight.Bold,
            style = Typography.bodyLarge,
            color = Color.Black,
            textAlign = TextAlign.Justify,
        )
        Spacer(modifier = Modifier.padding(8.dp))
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = benefitsDetail.content,
            style = Typography.bodyMedium,
            color = Color.Black,
            textAlign = TextAlign.Justify,
        )
    }
}