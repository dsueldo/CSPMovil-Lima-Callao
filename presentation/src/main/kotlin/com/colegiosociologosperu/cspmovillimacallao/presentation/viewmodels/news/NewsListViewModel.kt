package com.colegiosociologosperu.cspmovillimacallao.presentation.viewmodels.news

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
class NewsListViewModel @Inject constructor(
    private val repository: NewsRepository,
) : CspAppViewModel() {

    private val _newsList = MutableStateFlow<List<News>>(emptyList())
    val newsList: StateFlow<List<News>> = _newsList

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _errorMessage = MutableStateFlow("")
    val errorMessage: StateFlow<String> = _errorMessage

    private val _uiState = MutableStateFlow(false)
    val uiState: StateFlow<Boolean> = _uiState

    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing: StateFlow<Boolean> = _isRefreshing

    init {
        fetchNewsList()
    }

    private fun fetchNewsList() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                _newsList.value = repository.getAllNews()
                _uiState.value = true
                _isLoading.value = false
            } catch (e: Exception) {
                _errorMessage.value = "Error al cargar las noticias: ${e.localizedMessage}"
                println("Error fetching news in ViewModel: ${e.localizedMessage}")
                _uiState.value = false
                _isLoading.value = false
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun refreshNewsList() {
        _isRefreshing.value = true
        fetchNewsList()
        _isRefreshing.value = false
    }
}