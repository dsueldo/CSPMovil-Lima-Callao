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

    fun validateFields(): Boolean {
        return _profileUiState.value.name.isNotEmpty() &&
                _profileUiState.value.lastName.isNotEmpty() &&
                _profileUiState.value.email.isNotEmpty() &&
                _profileUiState.value.phone.isNotEmpty() &&
                _profileUiState.value.birthday.isNotEmpty() &&
                _profileUiState.value.gender.isNotEmpty() &&
                _profileUiState.value.codeNumber.isNotEmpty()
    }

    fun saveProfile() {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                val currentProfile = profileUseCase.getProfileData() // Debe devolver ProfileUiState
                val newProfile = _profileUiState.value

                val updatedProfile = currentProfile.copy(
                    name = if (newProfile.name.isNotBlank()) newProfile.name else currentProfile.name,
                    lastName = if (newProfile.lastName.isNotBlank()) newProfile.lastName else currentProfile.lastName,
                    email = if (newProfile.email.isNotBlank()) newProfile.email else currentProfile.email,
                    phone = if (newProfile.phone.isNotBlank()) newProfile.phone else currentProfile.phone,
                    birthday = if (newProfile.birthday.isNotBlank()) newProfile.birthday else currentProfile.birthday,
                    gender = if (newProfile.gender.isNotBlank()) newProfile.gender else currentProfile.gender,
                    dni = if (newProfile.dni.isNotBlank()) newProfile.dni else currentProfile.dni,
                    codeNumber = if (newProfile.codeNumber.isNotBlank()) newProfile.codeNumber else currentProfile.codeNumber,
                    specialized = if (newProfile.specialized.isNotBlank()) newProfile.specialized else currentProfile.specialized,
                    condition = if (newProfile.condition.isNotBlank()) newProfile.condition else currentProfile.condition,
                    payLastPeriod = if (newProfile.payLastPeriod.isNotBlank()) newProfile.payLastPeriod else currentProfile.payLastPeriod,
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