package com.colegiosociologosperu.cspmovillimacallao.presentation.viewmodels.auth.splash

import androidx.lifecycle.viewModelScope
import com.colegiosociologosperu.cspmovillimacallao.domain.repositories.AuthRepository
import com.colegiosociologosperu.cspmovillimacallao.presentation.viewmodels.CspAppViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
  private val authRepository: AuthRepository,
) : CspAppViewModel() {

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
