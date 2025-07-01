package com.colegiosociologosperu.cspmovillimacallao.presentation.viewmodels.auth.changepassword

import android.util.Log
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.viewModelScope
import com.colegiosociologosperu.cspmovillimacallao.data.repositories.EmailValidationService
import com.colegiosociologosperu.cspmovillimacallao.domain.repositories.AuthRepository
import com.colegiosociologosperu.cspmovillimacallao.presentation.viewmodels.CspAppViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChangePasswordViewModel @Inject constructor(
    private val authRepository: AuthRepository,
): CspAppViewModel() {

    private val _email = MutableStateFlow(TextFieldValue(""))
    val email: StateFlow<TextFieldValue> = _email

    private val _confirmEmail = MutableStateFlow(TextFieldValue(""))
    val confirmEmail: StateFlow<TextFieldValue> = _confirmEmail

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _errorMessage = MutableStateFlow("")
    val errorMessage: StateFlow<String> = _errorMessage

    private val _isEmailSent = MutableStateFlow(false)
    val isEmailSent: StateFlow<Boolean> = _isEmailSent

    private val _uiState = MutableStateFlow(false)
    val uiState: StateFlow<Boolean> = _uiState

    private val emailValidationService = EmailValidationService()

    fun resetState() {
        _uiState.value = false
        _errorMessage.value = ""
    }

    fun updateEmail(newEmail: TextFieldValue) {
        _email.value = newEmail
        _errorMessage.value = ""
    }

    fun updateConfirmEmail(newConfirmEmail: TextFieldValue) {
        _confirmEmail.value = newConfirmEmail
        _errorMessage.value = ""
    }

    fun validateEnterEmail(): Boolean {
        val isValid = emailValidationService.validate(_email.value)
        if (!isValid) {
            _errorMessage.value = emailValidationService.getErrorMessage()
            Log.d("SignUpViewModel", "Error: ${_errorMessage.value}")
        }
        return isValid
    }

    fun validateEnterConfirmEmail(): Boolean {
        val isValid = emailValidationService.validate(_confirmEmail.value)
        if (!isValid) {
            _errorMessage.value = emailValidationService.getErrorMessage()
            Log.d("SignUpViewModel", "Error: ${_errorMessage.value}")
        }
        return isValid
    }

    fun sendPasswordResetEmail() {

        if (!validateEnterEmail() || !validateEnterConfirmEmail()) { return }
        _isLoading.value = true
        viewModelScope.launch {
            try {
                authRepository.sendPasswordResetEmail(_email.value.text)
                _isEmailSent.value = true
                Log.d("ChangePasswordViewModel", "isEmailSent: ${authRepository.sendPasswordResetEmail(_isEmailSent.value.toString())}")
            } catch (e: Exception) {
                _errorMessage.value = when {
                    e.message?.contains("Failed to send password reset email. Please try again.") == true -> {
                        "Error al enviar el correo electrónico de restablecimiento de contraseña. Inténtelo de nuevo."
                    }
                    else -> {
                        "Ocurrio un error: ${e.message}"
                    }
                }
                Log.d("ChangePasswordViewModel", "Error: ${e.message}")
            } finally {
                _isLoading.value = false
            }
        }
    }
}