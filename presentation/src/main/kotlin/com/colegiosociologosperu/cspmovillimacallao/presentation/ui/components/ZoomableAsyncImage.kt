package com.colegiosociologosperu.cspmovillimacallao.presentation.ui.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage

@Composable
fun ZoomableAsyncImage(
    modifier: Modifier = Modifier,
    imageUrl: String,
    contentScale: ContentScale,
    contentDescription: String? = null,
    onClose: () -> Unit,
    minScale: Float = 1f,
    maxScale: Float = 3f,
    doubleTapZoomScale: Float = 2f,
    backgroundColor: Color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.9f),
    closeIconColor: Color = MaterialTheme.colorScheme.onBackground
) {
    var scale by remember { mutableStateOf(1f) }
    var offsetX by remember { mutableStateOf(0f) }
    var offsetY by remember { mutableStateOf(0f) }
    var isImageLoading by remember { mutableStateOf(true) }

    val animatedScale by animateFloatAsState(targetValue = scale)
    val animatedOffsetX by animateFloatAsState(targetValue = if (scale == 1f) 0f else offsetX)
    val animatedOffsetY by animateFloatAsState(targetValue = if (scale == 1f) 0f else offsetY)

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(backgroundColor)
            .pointerInput(Unit) {
                detectTransformGestures { _, pan, zoom, _ ->
                    scale = (scale * zoom).coerceIn(minScale, maxScale)
                    if (scale > 1f) {
                        offsetX += pan.x
                        offsetY += pan.y
                    } else {
                        offsetX = 0f
                        offsetY = 0f
                    }
                }
            }
            .pointerInput(Unit) {
                detectTapGestures(
                    onDoubleTap = {
                        scale = if (scale > minScale) minScale else doubleTapZoomScale
                        offsetX = 0f
                        offsetY = 0f
                    }
                )
            }
    ) {
        if (isImageLoading) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center),
                color = closeIconColor
            )
        }

        AsyncImage(
            model = imageUrl,
            contentDescription = contentDescription,
            modifier = Modifier
                .fillMaxSize()
                .graphicsLayer {
                    scaleX = animatedScale
                    scaleY = animatedScale
                    translationX = animatedOffsetX
                    translationY = animatedOffsetY
                },
            contentScale = contentScale,
            onLoading = { isImageLoading = true },
            onSuccess = { isImageLoading = false },
            onError = { isImageLoading = false }
        )

        IconButton(
            onClick = onClose,
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(16.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Close,
                contentDescription = "Close",
                tint = closeIconColor
            )
        }
    }
}