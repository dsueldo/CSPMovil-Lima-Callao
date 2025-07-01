package com.colegiosociologosperu.cspmovillimacallao.presentation.viewmodels.news.detail

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.colegiosociologosperu.cspmovillimacallao.domain.entities.news.News
import com.colegiosociologosperu.cspmovillimacallao.domain.repositories.NewsRepository
import com.colegiosociologosperu.cspmovillimacallao.presentation.viewmodels.CspAppViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsDetailViewModel @Inject constructor(
    private val repository: NewsRepository
) : CspAppViewModel() {

    private val _newsDetail = MutableStateFlow(News())
    val newsDetail: StateFlow<News> = _newsDetail

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _errorMessage = MutableStateFlow("")
    val errorMessage: StateFlow<String> = _errorMessage

    private val _uiState = MutableStateFlow(false)
    val uiState: StateFlow<Boolean> = _uiState

    fun fetchNewsDetail(newsId: String) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                _newsDetail.value = repository.getNewsDetail(newsId)
                _uiState.value = true
                _isLoading.value = false
                Log.d("NewsDetailViewModel", "News Detail: ${_newsDetail.value}")
            } catch (e: Exception) {
                _errorMessage.value = "Error al cargar el detalle de la noticia: ${e.localizedMessage}"
                _uiState.value = false
                _isLoading.value = false
            } finally {
                _isLoading.value = false
            }
        }
    }
}