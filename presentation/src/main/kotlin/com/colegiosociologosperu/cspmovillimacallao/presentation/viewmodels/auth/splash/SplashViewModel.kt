package com.colegiosociologosperu.cspmovillimacallao.presentation.viewmodels.auth.splash

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.colegiosociologosperu.cspmovillimacallao.domain.usecases.AuthUseCase
import com.colegiosociologosperu.cspmovillimacallao.domain.usecases.ProfileUseCase
import com.colegiosociologosperu.cspmovillimacallao.presentation.viewmodels.CspAppViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val authUseCase: AuthUseCase,
    private val profileUseCase: ProfileUseCase
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
            if (authUseCase.hasUser()) {
                try {
                    val profile = profileUseCase.getProfileData()
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
        return authUseCase.hasUser()
    }

    fun signOut() {
        viewModelScope.launch {
            authUseCase.signOut()
        }
    }

    fun deleteAccount() {
        viewModelScope.launch {
            authUseCase.deleteAccount()
        }
    }
}
