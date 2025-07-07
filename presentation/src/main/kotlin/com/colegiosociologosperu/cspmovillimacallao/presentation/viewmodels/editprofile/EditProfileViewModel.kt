package com.colegiosociologosperu.cspmovillimacallao.presentation.viewmodels.editprofile

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.colegiosociologosperu.cspmovillimacallao.domain.entities.user.ProfileUiState
import com.colegiosociologosperu.cspmovillimacallao.domain.usecases.ProfileUseCase
import com.colegiosociologosperu.cspmovillimacallao.presentation.viewmodels.CspAppViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditProfileViewModel @Inject constructor(
    private val profileUseCase: ProfileUseCase,
) : CspAppViewModel() {

    private val _uiState = MutableStateFlow(false)
    val uiState: StateFlow<Boolean> = _uiState

    private val _profileUiState = MutableStateFlow(ProfileUiState())
    val profileUiState: StateFlow<ProfileUiState> = _profileUiState

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _errorMessage = MutableStateFlow("")
    val errorMessage: StateFlow<String> = _errorMessage

    private val auth = FirebaseAuth.getInstance()

    private val currentUser = auth.currentUser

    init {
        getEmail()
    }

    private fun getEmail() {
        currentUser?.let {
            _profileUiState.value = _profileUiState.value.copy(email = it.email.orEmpty())
            Log.d("EditProfileViewModel", "getEmail: ${it.email}")
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
        _isLoading.value = true
        viewModelScope.launch {
            try {
                val currentProfile = profileUseCase.getProfileData() // Debe devolver ProfileUiState
                val newProfile = _profileUiState.value

                val updatedProfile = currentProfile.copy(
                    name = newProfile.name.ifBlank { currentProfile.name },
                    lastName = newProfile.lastName.ifBlank { currentProfile.lastName },
                    email = newProfile.email.ifBlank { currentProfile.email },
                    phone = newProfile.phone.ifBlank { currentProfile.phone },
                    birthday = newProfile.birthday.ifBlank { currentProfile.birthday },
                    gender = newProfile.gender.ifBlank { currentProfile.gender },
                    dni = newProfile.dni.ifBlank { currentProfile.dni },
                    codeNumber = newProfile.codeNumber.ifBlank { currentProfile.codeNumber },
                    specialized = newProfile.specialized.ifBlank { currentProfile.specialized },
                    condition = newProfile.condition.ifBlank { currentProfile.condition },
                    payLastPeriod = newProfile.payLastPeriod.ifBlank { currentProfile.payLastPeriod },
                )
                profileUseCase.saveProfileData(updatedProfile)
                _uiState.value = true
                Log.d("EditProfileViewModel", "profile: $updatedProfile")
            } catch (e: Exception) {
                _uiState.value = false
                _errorMessage.value = e.message ?: "Error al guardar el perfil"
            } finally {
                _isLoading.value = false
            }
        }
    }
}