package com.colegiosociologosperu.cspmovillimacallao.presentation.viewmodels.auth.splash

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.colegiosociologosperu.cspmovillimacallao.domain.repositories.AuthRepository
import com.colegiosociologosperu.cspmovillimacallao.domain.repositories.UserProfileService
import com.colegiosociologosperu.cspmovillimacallao.presentation.viewmodels.CspAppViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val userProfileService: UserProfileService
) : CspAppViewModel() {

    private val _userRole = mutableStateOf<String?>(null)
    val userRole: State<String?> = _userRole

    private val _isLoading = mutableStateOf(true)
    val isLoading: State<Boolean> = _isLoading

    init {
        checkUserStatus()
    }

    fun checkUserStatus() {
        viewModelScope.launch {
            _isLoading.value = true
            if (authRepository.hasUser()) {
                try {
                    val profile = userProfileService.getProfile()
                    _userRole.value = profile.role
                } catch (e: Exception) {
                    _userRole.value = "user" // Default if error
                }
            } else {
                _userRole.value = null
            }
            _isLoading.value = false
        }
    }

    fun hasUser(): Boolean {
        return authRepository.hasUser()
    }

  fun signOut() {
    viewModelScope.launch {
      authRepository.signOut()
    }
  }

  fun deleteAccount() {
    viewModelScope.launch {
      authRepository.deleteAccount()
      }
  }
}
