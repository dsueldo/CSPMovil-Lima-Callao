package com.colegiosociologosperu.cspmovillimacallao.presentation.viewmodels.editprofile

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.colegiosociologosperu.cspmovillimacallao.domain.entities.user.ProfileUiState
import com.colegiosociologosperu.cspmovillimacallao.domain.usecases.ProfileUseCase
import com.colegiosociologosperu.cspmovillimacallao.presentation.viewmodels.CspAppViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditProfileViewModel @Inject constructor(
    private val profileUseCase: ProfileUseCase,
) : CspAppViewModel() {

    private val _isSaved = MutableStateFlow(false)
    val isSaved: StateFlow<Boolean> = _isSaved.asStateFlow()

    private val _profileUiState = MutableStateFlow(ProfileUiState())
    val profileUiState: StateFlow<ProfileUiState> = _profileUiState.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _errorMessage = MutableStateFlow("")
    val errorMessage: StateFlow<String> = _errorMessage.asStateFlow()

    init {
        loadProfileData()
    }

    private fun loadProfileData() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val profile = profileUseCase.getProfileData()
                _profileUiState.value = profile
            } catch (e: Exception) {
                _errorMessage.value = "Error al cargar los datos del perfil"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun updateName(name: String) {
        _profileUiState.value = _profileUiState.value.copy(name = name)
    }

    fun updateLastName(lastName: String) {
        _profileUiState.value = _profileUiState.value.copy(lastName = lastName)
    }

    fun updateEmail(email: String) {
        _profileUiState.value = _profileUiState.value.copy(email = email.trim())
    }

    fun updatePhoneNumber(phone: String) {
        _profileUiState.value = _profileUiState.value.copy(phone = phone.trim())
    }

    fun updateBirthday(birthday: String) {
        _profileUiState.value = _profileUiState.value.copy(birthday = birthday.trim())
    }

    fun updateGender(gender: String) {
        _profileUiState.value = _profileUiState.value.copy(gender = gender.trim())
    }

    fun updateDni(dni: String) {
        _profileUiState.value = _profileUiState.value.copy(dni = dni.trim())
    }

    fun updateCodeNumber(codeNumber: String) {
        _profileUiState.value = _profileUiState.value.copy(codeNumber = codeNumber.trim())
    }

    fun updateSpecialized(specialized: String) {
        _profileUiState.value = _profileUiState.value.copy(specialized = specialized.trim())
    }

    fun saveProfile() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                profileUseCase.saveProfileData(_profileUiState.value)
                _isSaved.value = true
                Log.d("EditProfileViewModel", "profile saved: ${_profileUiState.value}")
            } catch (e: Exception) {
                _errorMessage.value = e.message ?: "Error al guardar el perfil"
            } finally {
                _isLoading.value = false
            }
        }
    }
    
    fun clearErrorMessage() {
        _errorMessage.value = ""
    }
}
