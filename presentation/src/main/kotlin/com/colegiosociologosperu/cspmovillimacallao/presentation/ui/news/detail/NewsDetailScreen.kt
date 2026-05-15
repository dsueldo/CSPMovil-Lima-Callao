package com.colegiosociologosperu.cspmovillimacallao.presentation.ui.news.detail

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarBorder
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.colegiosociologosperu.cspmovillimacallao.domain.entities.news.News
import com.colegiosociologosperu.cspmovillimacallao.presentation.ui.components.ZoomableAsyncImage
import com.colegiosociologosperu.cspmovillimacallao.presentation.utils.theme.Red_Dark
import com.colegiosociologosperu.cspmovillimacallao.presentation.utils.theme.Typography
import com.colegiosociologosperu.cspmovillimacallao.presentation.viewmodels.news.detail.NewsDetailViewModel
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NewsDetailScreen(
    newsId: String,
    navController: NavController,
    modifier: Modifier = Modifier,
    viewModel: NewsDetailViewModel = hiltViewModel(),
) {
    val newsDetail by viewModel.newsDetail.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()
    val isFavorite by viewModel.isFavorite.collectAsState()
    val isDeleted by viewModel.isDeleted.collectAsState()
    val adminProfile by viewModel.adminProfile.collectAsState()

    LaunchedEffect(newsId) {
        viewModel.fetchNewsDetail(newsId)
    }

    LaunchedEffect(isDeleted) {
        if (isDeleted) {
            navController.popBackStack()
        }
    }

    NewsDetailContent(
        newsDetail = newsDetail,
        isLoading = isLoading,
        errorMessage = errorMessage,
        isFavorite = isFavorite,
        isAdmin = adminProfile?.role == "admin",
        onBack = { navController.popBackStack() },
        onFavoriteToggle = { viewModel.toggleFavorite(newsId) },
        onUpdateNews = { updatedNews -> viewModel.updateNews(updatedNews) },
        onDeleteNews = { viewModel.deleteNews(newsId) },
        modifier = modifier
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NewsDetailContent(
    newsDetail: News,
    isLoading: Boolean,
    errorMessage: String,
    isFavorite: Boolean,
    isAdmin: Boolean,
    onBack: () -> Unit,
    onFavoriteToggle: () -> Unit,
    onUpdateNews: (News) -> Unit,
    onDeleteNews: () -> Unit,
    modifier: Modifier = Modifier
) {
    var fullscreenImageState by remember { mutableStateOf<String?>(null) }
    var showEditDialog by remember { mutableStateOf(false) }
    var showDeleteDialog by remember { mutableStateOf(false) }

    if (showEditDialog) {
        EditNewsDialog(
            news = newsDetail,
            onDismiss = { showEditDialog = false },
            onConfirm = { updatedNews ->
                onUpdateNews(updatedNews)
                showEditDialog = false
            }
        )
    }

    if (showDeleteDialog) {
        AlertDialog(
            onDismissRequest = { showDeleteDialog = false },
            title = { Text("Eliminar noticia", fontWeight = FontWeight.Bold) },
            text = { Text("¿Estás seguro de que deseas eliminar esta noticia? Esta acción no se puede deshacer.") },
            confirmButton = {
                Button(onClick = {
                    onDeleteNews()
                    showDeleteDialog = false
                },
                    colors = ButtonDefaults.buttonColors(containerColor = Red_Dark)
                ){
                    Text("Eliminar")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDeleteDialog = false }) {
                    Text("Cancelar", color = Red_Dark)
                }
            }
        )
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Scaffold(
            modifier = modifier,
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            text = "Detalle de la noticia",
                            style = Typography.headlineSmall,
                            fontWeight = FontWeight.Bold
                        )
                    },
                    navigationIcon = {
                        IconButton(onClick = onBack) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Atrás"
                            )
                        }
                    },
                    actions = {
                        if (isAdmin) {
                            IconButton(onClick = { showEditDialog = true }) {
                                Icon(
                                    imageVector = Icons.Default.Edit,
                                    contentDescription = "Editar noticia",
                                    tint = Red_Dark
                                )
                            }
                            IconButton(onClick = { showDeleteDialog = true }) {
                                Icon(
                                    imageVector = Icons.Default.Delete,
                                    contentDescription = "Eliminar noticia",
                                    tint = Red_Dark
                                )
                            }
                        }
                        IconButton(onClick = onFavoriteToggle) {
                            Icon(
                                imageVector = if (isFavorite) Icons.Default.Star else Icons.Default.StarBorder,
                                contentDescription = if (isFavorite) "Quitar de favoritos" else "Agregar a favoritos",
                                tint = if (isFavorite) Red_Dark else MaterialTheme.colorScheme.onSurface
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
                    .background(MaterialTheme.colorScheme.background)
            ) {
                if (newsDetail.id.isNotEmpty()) {
                    NewsDetailBody(
                        newsDetail = newsDetail,
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

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditNewsDialog(
    news: News,
    onDismiss: () -> Unit,
    onConfirm: (News) -> Unit
) {
    var title by remember { mutableStateOf(news.title) }
    var description by remember { mutableStateOf(news.description) }
    var content by remember { mutableStateOf(news.content) }
    var source by remember { mutableStateOf(news.source) }
    var imageUrl by remember { mutableStateOf(news.image) }
    var date by remember { mutableStateOf(news.date) }

    var showDatePicker by remember { mutableStateOf(false) }
    var showTimePicker by remember { mutableStateOf(false) }
    val datePickerState = rememberDatePickerState()
    val timePickerState = rememberTimePickerState()

    if (showDatePicker) {
        DatePickerDialog(
            onDismissRequest = { showDatePicker = false },
            confirmButton = {
                TextButton(onClick = {
                    showDatePicker = false
                    showTimePicker = true
                },
                colors = ButtonDefaults.textButtonColors(contentColor = Red_Dark)
                ){
                    Text("Siguiente", color = Red_Dark)
                }
            },
            dismissButton = {
                TextButton(onClick = { showDatePicker = false }) {
                    Text("Cancelar", color = Red_Dark)
                }
            }
        ) {
            DatePicker(
                state = datePickerState,
                colors = DatePickerDefaults.colors(
                    todayContentColor = Red_Dark,
                    todayDateBorderColor = Red_Dark,
                    selectedDayContainerColor = Red_Dark,
                    selectedDayContentColor = Color.White,
                    selectedYearContainerColor = Red_Dark,
                    selectedYearContentColor = Color.White,
                    headlineContentColor = Red_Dark,
                    titleContentColor = Red_Dark
                )
            )
        }
    }

    if (showTimePicker) {
        AlertDialog(
            onDismissRequest = { showTimePicker = false },
            confirmButton = {
                TextButton(
                    onClick = {
                    val selectedDate = datePickerState.selectedDateMillis?.let {
                        Instant.ofEpochMilli(it).atZone(ZoneId.systemDefault()).toLocalDate()
                    } ?: LocalDateTime.now().toLocalDate()

                    val selectedDateTime = selectedDate.atTime(timePickerState.hour, timePickerState.minute)
                    date = selectedDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss"))
                    showTimePicker = false
                },
                    colors = ButtonDefaults.textButtonColors(contentColor = Red_Dark)
                ){
                    Text("OK", color = Red_Dark)
                }
            },
            dismissButton = {
                TextButton(onClick = { showTimePicker = false }) {
                    Text("Cancelar", color = Red_Dark)
                }
            },
            title = { Text("Seleccionar Hora", color = Red_Dark, fontWeight = FontWeight.Bold) },
            text = {
                Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxWidth()) {
                    TimePicker(
                        state = timePickerState,
                        colors = TimePickerDefaults.colors(
                            selectorColor = Red_Dark,
                            periodSelectorSelectedContainerColor = Red_Dark.copy(alpha = 0.2f),
                            periodSelectorSelectedContentColor = Red_Dark,
                            timeSelectorSelectedContainerColor = Red_Dark.copy(alpha = 0.2f),
                            timeSelectorSelectedContentColor = Red_Dark
                        )
                    )
                }
            }
        )
    }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Editar Noticia", color = Red_Dark, fontWeight = FontWeight.Bold) },
        text = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text("Título") },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Red_Dark,
                        focusedLabelColor = Red_Dark
                    )
                )
                OutlinedTextField(
                    value = description,
                    onValueChange = { description = it },
                    label = { Text("Descripción") },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Red_Dark,
                        focusedLabelColor = Red_Dark
                    )
                )
                OutlinedTextField(
                    value = content,
                    onValueChange = { content = it },
                    label = { Text("Contenido") },
                    minLines = 3,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Red_Dark,
                        focusedLabelColor = Red_Dark
                    )
                )
                OutlinedTextField(
                    value = source,
                    onValueChange = { source = it },
                    label = { Text("Fuente") },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Red_Dark,
                        focusedLabelColor = Red_Dark
                    )
                )
                OutlinedTextField(
                    value = imageUrl,
                    onValueChange = { imageUrl = it },
                    label = { Text("URL Imagen") },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Red_Dark,
                        focusedLabelColor = Red_Dark
                    )
                )
                OutlinedTextField(
                    value = date,
                    onValueChange = { date = it },
                    label = { Text("Fecha") },
                    readOnly = true,
                    trailingIcon = {
                        IconButton(onClick = { showDatePicker = true }) {
                            Icon(Icons.Default.CalendarMonth, contentDescription = null)
                        }
                    },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Red_Dark,
                        focusedLabelColor = Red_Dark
                    )
                )
            }
        },
        confirmButton = {
            Button(onClick = {
                onConfirm(news.copy(
                    title = title,
                    description = description,
                    content = content,
                    source = source,
                    image = imageUrl,
                    date = date
                ))
            },
                colors = ButtonDefaults.buttonColors(containerColor = Red_Dark)
            ){
                Text("Guardar")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancelar", color = Red_Dark)
            }
        }
    )
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun NewsDetailScreenPreview() {
    val mockNews = News(
        id = "1",
        title = "Nueva app lanzada",
        description = "Se ha lanzado la nueva aplicación del Colegio de Sociólogos del Perú.",
        content = "Contenido detallado de la noticia...",
        date = "2026-03-27T10:00:00",
        source = "CSP"
    )
    MaterialTheme {
        NewsDetailContent(
            newsDetail = mockNews,
            isLoading = false,
            errorMessage = "",
            isFavorite = true,
            isAdmin = true,
            onBack = {},
            onFavoriteToggle = {},
            onUpdateNews = {},
            onDeleteNews = {}
        )
    }
}
