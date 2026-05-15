package com.colegiosociologosperu.cspmovillimacallao.presentation.viewmodels.news.add

import androidx.lifecycle.viewModelScope
import com.colegiosociologosperu.cspmovillimacallao.domain.entities.news.News
import com.colegiosociologosperu.cspmovillimacallao.domain.usecases.NewsUseCase
import com.colegiosociologosperu.cspmovillimacallao.presentation.viewmodels.CspAppViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddNewsViewModel @Inject constructor(
    private val newsUseCase: NewsUseCase
) : CspAppViewModel() {

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private val _isSuccess = MutableStateFlow(false)
    val isSuccess = _isSuccess.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error = _error.asStateFlow()

    fun addNews(
        image: String,
        title: String,
        content: String,
        description: String,
        source: String,
        date: String
    ) {
        if (title.isBlank() || content.isBlank() || description.isBlank() || source.isBlank() || date.isBlank()) {
            _error.value = "Por favor, complete todos los campos obligatorios"
            return
        }

        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            try {
                val newNews = News(
                    image = image,
                    title = title,
                    content = content,
                    description = description,
                    source = source,
                    date = date
                    // order is now handled automatically in the repository
                )
                newsUseCase.addNews(newNews)
                _isSuccess.value = true
            } catch (e: Exception) {
                _error.value = "Error al guardar la noticia: ${e.localizedMessage}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun resetState() {
        _isSuccess.value = false
        _error.value = null
    }
}
