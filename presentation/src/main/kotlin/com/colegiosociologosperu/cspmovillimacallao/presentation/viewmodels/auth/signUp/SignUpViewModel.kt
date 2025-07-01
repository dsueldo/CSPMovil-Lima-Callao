package com.colegiosociologosperu.cspmovillimacallao.presentation.viewmodels.auth.signUp

import android.util.Log
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.viewModelScope
import com.colegiosociologosperu.cspmovillimacallao.data.repositories.EmailValidationService
import com.colegiosociologosperu.cspmovillimacallao.data.repositories.PasswordValidationService
import com.colegiosociologosperu.cspmovillimacallao.domain.repositories.AuthRepository
import com.colegiosociologosperu.cspmovillimacallao.presentation.viewmodels.CspAppViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : CspAppViewModel() {

    private val _email = MutableStateFlow(TextFieldValue(""))
    val email: StateFlow<TextFieldValue> = _email

    private val _password = MutableStateFlow(TextFieldValue(""))
    val password: StateFlow<TextFieldValue> = _password

    private val _confirmPassword = MutableStateFlow(TextFieldValue(""))
    val confirmPassword: StateFlow<TextFieldValue> = _confirmPassword

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _errorMessage = MutableStateFlow("")
    val errorMessage: StateFlow<String> = _errorMessage

    private val _uiState = MutableStateFlow(false)
    val uiState: StateFlow<Boolean> = _uiState

    private val emailValidationService = EmailValidationService()
    private val passwordValidationService = PasswordValidationService()

    fun resetState() {
        _uiState.value = false
        _errorMessage.value = ""
    }

    fun updateEmail(newEmail: TextFieldValue) {
        _email.value = newEmail.copy(
            text = newEmail.text.trim(),
            selection = newEmail.selection
        )
        _errorMessage.value = ""
    }

    fun updatePassword(newPassword: TextFieldValue) {
        _password.value = newPassword.copy(
            text = newPassword.text.trim(),
            selection = newPassword.selection
        )
        _errorMessage.value = ""
    }

    fun updateConfirmPassword(newConfirmPassword: TextFieldValue) {
        _confirmPassword.value = newConfirmPassword.copy(
            text = newConfirmPassword.text.trim(),
            selection = newConfirmPassword.selection
        )
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

    fun validateEnterPassword(): Boolean {
        val isValid = passwordValidationService.validate(_password.value)
        if (!isValid) {
            _errorMessage.value = passwordValidationService.getErrorMessage()
            Log.d("SignUpViewModel", "Error: ${_errorMessage.value}")
        }
        return isValid
    }

    fun validateEnterConfirmPassword(): Boolean {
        val isValid = passwordValidationService.validate(_confirmPassword.value)
        if (!isValid) {
            _errorMessage.value = passwordValidationService.getErrorMessage()
            Log.d("SignUpViewModel", "Error: ${_errorMessage.value}")
        }
        return isValid
    }

    fun validatePasswordComplexity(password: String): Boolean {
        val hasUppercase = password.any { it.isUpperCase() }
        val hasLowercase = password.any { it.isLowerCase() }
        val hasDigit = password.any { it.isDigit() }
        val hasSpecialChar = password.any { !it.isLetterOrDigit() }
        val isValidLength = password.length >= 8
        return hasUppercase && hasLowercase && hasDigit && hasSpecialChar && isValidLength
    }

    fun validatePasswordStrength(password: String): String {
        return when {
            password.length < 6 -> "La contraseña es muy corta"
            !password.any { it.isDigit() } -> "La contraseña debe contener al menos un dígito"
            !password.any { it.isUpperCase() } -> "La contraseña debe contener al menos una letra mayúscula"
            !password.any { it.isLowerCase() } -> "La contraseña debe contener al menos una letra minúscula"
            !password.any { !it.isLetterOrDigit() } -> "La contraseña debe contener al menos un carácter especial"
            else -> "La contraseña es fuerte"
        }
    }

    fun onSignUpClick() {

        if (!validateEnterEmail() || !validateEnterPassword() || !validateEnterConfirmPassword()) { return }
        _isLoading.value = true
        viewModelScope.launch {
            try {
                authRepository.signUp(email = _email.value.text, password = _password.value.text)
                _uiState.value = true
            } catch (e: Exception) {
                _uiState.value = false
                _errorMessage.value = when {
                    e.message?.contains("The email address is already in use by another account.") == true -> {
                        "El correo ya se encuentra registrado."
                    }
                    e.message?.contains("Email is not allowed") == true -> {
                        "El correo no puede ser registrado."
                    }
                    e.message?.contains("Network error") == true -> {
                        "Error de conexión. Por favor, inténtelo de nuevo."
                    }
                    e.message?.contains("Weak password") == true -> {
                        "La contraseña es demasiado débil."
                    }
                    else -> {
                        "Ocurrió un error: ${e.message}"
                    }
                }
                Log.d("SignUpViewModel", "Error: ${e.message}")
            } finally {
                _isLoading.value = false
            }
        }
    }
}