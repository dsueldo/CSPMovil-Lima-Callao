package com.colegiosociologosperu.cspmovillimacallao.presentation.ui.news.detail

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import com.colegiosociologosperu.cspmovillimacallao.presentation.viewmodels.news.detail.NewsDetailViewModel

@Composable
fun NewsDetailBody(
    modifier: Modifier = Modifier,
    viewModel: NewsDetailViewModel,
    onImageClick: (String) -> Unit
) {
    val newsDetail by viewModel.newsDetail.collectAsState()

    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AsyncImage(
            modifier = modifier
                .fillMaxWidth()
                .fillMaxSize()
                .clickable { onImageClick(newsDetail.image) },
            model = newsDetail.image,
            contentScale = ContentScale.Crop,
            contentDescription = null,
        )
        Spacer(modifier = Modifier.padding(8.dp))
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = newsDetail.title,
            fontWeight = FontWeight.Bold,
            style = Typography.bodyLarge,
            color = Color.Black,
            textAlign = TextAlign.Justify,
        )
        Spacer(modifier = Modifier.padding(8.dp))
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = newsDetail.content,
            style = Typography.bodyMedium,
            color = Color.Black,
            textAlign = TextAlign.Justify,
        )
        Spacer(modifier = Modifier.padding(8.dp))
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = newsDetail.description,
            style = Typography.bodyMedium,
            color = Color.Black,
            textAlign = TextAlign.Justify,
        )
    }
}