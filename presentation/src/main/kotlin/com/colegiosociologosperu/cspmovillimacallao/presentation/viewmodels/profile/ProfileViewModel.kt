package com.colegiosociologosperu.cspmovillimacallao.presentation.viewmodels.profile

import androidx.lifecycle.viewModelScope
import com.colegiosociologosperu.cspmovillimacallao.domain.entities.user.ProfileUiState
import com.colegiosociologosperu.cspmovillimacallao.domain.usecases.AuthUseCase
import com.colegiosociologosperu.cspmovillimacallao.domain.usecases.ProfileUseCase
import com.colegiosociologosperu.cspmovillimacallao.presentation.viewmodels.CspAppViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val profileUseCase: ProfileUseCase,
    private val authUseCase: AuthUseCase
) : CspAppViewModel() {

    private val _profileUiState = MutableStateFlow(ProfileUiState())
    val profileUiState: StateFlow<ProfileUiState> = _profileUiState.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _errorMessage = MutableStateFlow("")
    val errorMessage: StateFlow<String> = _errorMessage.asStateFlow()

    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing: StateFlow<Boolean> = _isRefreshing.asStateFlow()

    private val _userEmail = MutableStateFlow("")
    val userEmail: StateFlow<String> = _userEmail.asStateFlow()

    init {
        fetchUserProfileData()
    }

    fun fetchUserProfileData() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val profile = profileUseCase.getProfileData()
                val user = authUseCase.currentUser.first()
                _userEmail.value = user?.email.orEmpty()
                _profileUiState.value = profile
            } catch (e: Exception) {
                _errorMessage.value = "Error fetching profile data: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun refreshProfile() {
        viewModelScope.launch {
            _isRefreshing.value = true
            try {
                val profile = profileUseCase.getProfileData()
                _profileUiState.value = profile
            } catch (e: Exception) {
                _errorMessage.value = e.message ?: "Error al refrescar el perfil"
            } finally {
                _isRefreshing.value = false
            }
        }
    }

    fun clearErrorMessage() {
        _errorMessage.value = ""
    }
}
