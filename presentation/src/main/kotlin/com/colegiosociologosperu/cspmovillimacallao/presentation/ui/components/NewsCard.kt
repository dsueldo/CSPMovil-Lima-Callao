package com.colegiosociologosperu.cspmovillimacallao.presentation.ui.components

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.colegiosociologosperu.cspmovillimacallao.domain.entities.news.News
import com.colegiosociologosperu.cspmovillimacallao.presentation.utils.theme.Typography
import com.colegiosociologosperu.cspmovillimacallao.presentation.utils.time.getRelativeTime
import kotlinx.coroutines.delay

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NewsCard(
    modifier: Modifier = Modifier,
    news: News,
    onNewsClick: (News) -> Unit
) {

    var relativeTime by remember { mutableStateOf(getRelativeTime(news.date)) }

    LaunchedEffect(news.date) {
        while (true) {
            relativeTime = getRelativeTime(news.date)
            delay(60_000) // Actualiza cada minuto
        }
    }

    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onNewsClick(news) },
        shape = MaterialTheme.shapes.medium,
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            AsyncImage(
                modifier = Modifier.fillMaxWidth(),
                model = news.image,
                contentScale = ContentScale.Crop,
                contentDescription = null
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = "Fuente: ${news.source}",
                style = Typography.bodySmall,
                color = Color.Black,
                textAlign = TextAlign.Start,
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = news.title,
                fontWeight = FontWeight.Bold,
                style = Typography.bodyLarge,
                color = Color.Black,
                textAlign = TextAlign.Justify,
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = news.content,
                style = Typography.bodyMedium,
                color = Color.Black,
                textAlign = TextAlign.Justify,
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = relativeTime,
                style = Typography.bodySmall,
                color = Color.Black,
                textAlign = TextAlign.Start,
            )
        }
    }
}