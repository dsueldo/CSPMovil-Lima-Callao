package com.colegiosociologosperu.cspmovillimacallao.presentation.viewmodels.news.favorites

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
class FavoritesViewModel @Inject constructor(
    private val repository: NewsRepository
) : CspAppViewModel() {

    private val _favoriteNews = MutableStateFlow<List<News>>(emptyList())
    val favoriteNews: StateFlow<List<News>> = _favoriteNews

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    init {
        fetchFavorites()
    }

    fun fetchFavorites() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                _favoriteNews.value = repository.getFavoriteNews()
            } catch (e: Exception) {
                // Handle error
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun toggleFavorite(newsId: String) {
        viewModelScope.launch {
            repository.toggleFavorite(newsId)
            fetchFavorites()
        }
    }
}
