package com.colegiosociologosperu.cspmovillimacallao.presentation.viewmodels.news.detail

import androidx.lifecycle.viewModelScope
import com.colegiosociologosperu.cspmovillimacallao.domain.entities.news.News
import com.colegiosociologosperu.cspmovillimacallao.domain.entities.user.ProfileUiState
import com.colegiosociologosperu.cspmovillimacallao.domain.usecases.NewsUseCase
import com.colegiosociologosperu.cspmovillimacallao.domain.usecases.ProfileUseCase
import com.colegiosociologosperu.cspmovillimacallao.presentation.viewmodels.CspAppViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsDetailViewModel @Inject constructor(
    private val newsUseCase: NewsUseCase,
    private val profileUseCase: ProfileUseCase
) : CspAppViewModel() {

    private val _newsDetail = MutableStateFlow(News())
    val newsDetail: StateFlow<News> = _newsDetail

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _errorMessage = MutableStateFlow("")
    val errorMessage: StateFlow<String> = _errorMessage

    private val _isFavorite = MutableStateFlow(false)
    val isFavorite: StateFlow<Boolean> = _isFavorite

    private val _isDeleted = MutableStateFlow(false)
    val isDeleted: StateFlow<Boolean> = _isDeleted.asStateFlow()

    private val _adminProfile = MutableStateFlow<ProfileUiState?>(null)
    val adminProfile = _adminProfile.asStateFlow()

    init {
        fetchAdminProfile()
    }

    private fun fetchAdminProfile() {
        viewModelScope.launch {
            try {
                _adminProfile.value = profileUseCase.getProfileData()
            } catch (e: Exception) {
                // Ignore error for profile
            }
        }
    }

    fun fetchNewsDetail(newsId: String) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                _newsDetail.value = newsUseCase.getNewsDetail(newsId)
                _isFavorite.value = newsUseCase.isFavorite(newsId)
            } catch (e: Exception) {
                _errorMessage.value = "Error al cargar el detalle de la noticia: ${e.localizedMessage}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun toggleFavorite(newsId: String) {
        viewModelScope.launch {
            newsUseCase.toggleFavorite(newsId)
            _isFavorite.value = newsUseCase.isFavorite(newsId)
        }
    }

    fun updateNews(news: News) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                newsUseCase.updateNews(news)
                _newsDetail.value = news
            } catch (e: Exception) {
                _errorMessage.value = "Error al actualizar la noticia: ${e.localizedMessage}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun deleteNews(newsId: String) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                newsUseCase.deleteNews(newsId)
                _isDeleted.value = true
            } catch (e: Exception) {
                _errorMessage.value = "Error al eliminar la noticia: ${e.localizedMessage}"
            } finally {
                _isLoading.value = false
            }
        }
    }
}