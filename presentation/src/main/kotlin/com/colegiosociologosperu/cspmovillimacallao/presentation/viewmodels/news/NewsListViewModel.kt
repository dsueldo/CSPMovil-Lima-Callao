package com.colegiosociologosperu.cspmovillimacallao.presentation.viewmodels.news

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
class NewsListViewModel @Inject constructor(
    private val newsUseCase: NewsUseCase,
    private val profileUseCase: ProfileUseCase
) : CspAppViewModel() {

    private val _newsList = MutableStateFlow<List<News>>(emptyList())
    val newsList: StateFlow<List<News>> = _newsList

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _errorMessage = MutableStateFlow("")
    val errorMessage: StateFlow<String> = _errorMessage

    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing: StateFlow<Boolean> = _isRefreshing

    private val _adminProfile = MutableStateFlow<ProfileUiState?>(null)
    val adminProfile: StateFlow<ProfileUiState?> = _adminProfile.asStateFlow()

    init {
        fetchNewsList()
        fetchAdminProfile()
    }

    private fun fetchAdminProfile() {
        viewModelScope.launch {
            try {
                val profile = profileUseCase.getProfileData()
                _adminProfile.value = profile
            } catch (e: Exception) {
                // Ignore if not found or error
            }
        }
    }

    private fun fetchNewsList(isRefresh: Boolean = false) {
        viewModelScope.launch {
            if (isRefresh) {
                _isRefreshing.value = true
            } else {
                _isLoading.value = true
            }
            try {
                _newsList.value = newsUseCase.getAllNews()
            } catch (e: Exception) {
                _errorMessage.value = "Error al cargar las noticias: ${e.localizedMessage}"
            } finally {
                _isLoading.value = false
                _isRefreshing.value = false
            }
        }
    }

    fun refreshNewsList() {
        fetchNewsList(isRefresh = true)
    }
}