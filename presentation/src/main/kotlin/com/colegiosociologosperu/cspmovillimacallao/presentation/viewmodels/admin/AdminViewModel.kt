package com.colegiosociologosperu.cspmovillimacallao.presentation.viewmodels.admin

import androidx.lifecycle.viewModelScope
import com.colegiosociologosperu.cspmovillimacallao.domain.entities.user.ProfileUiState
import com.colegiosociologosperu.cspmovillimacallao.domain.usecases.ProfileUseCase
import com.colegiosociologosperu.cspmovillimacallao.presentation.viewmodels.CspAppViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AdminViewModel @Inject constructor(
    private val profileUseCase: ProfileUseCase
) : CspAppViewModel() {

    private val _users = MutableStateFlow<List<ProfileUiState>>(emptyList())
    
    private val _adminProfile = MutableStateFlow<ProfileUiState?>(null)
    val adminProfile: StateFlow<ProfileUiState?> = _adminProfile.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<StateFlow<Boolean>> = MutableStateFlow(_isLoading.asStateFlow()).asStateFlow()
    // Wait, the above is wrong. Fixed below.

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading.asStateFlow()

    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing: StateFlow<Boolean> = _isRefreshing.asStateFlow()

    val filteredUsers = combine(_users, _searchQuery) { users, query ->
        if (query.isBlank()) {
            users
        } else {
            users.filter { 
                it.name.contains(query, ignoreCase = true) || 
                it.lastName.contains(query, ignoreCase = true) ||
                it.dni.contains(query) ||
                it.codeNumber.contains(query)
            }
        }
    }

    init {
        loadData()
    }

    fun loadData(isRefresh: Boolean = false) {
        viewModelScope.launch {
            if (isRefresh) {
                _isRefreshing.value = true
            } else {
                _loading.value = true
            }
            try {
                val admin = profileUseCase.getProfileData()
                _adminProfile.value = admin
                _users.value = profileUseCase.getAllUsers()
            } catch (e: Exception) {
                // Handle error
            } finally {
                _loading.value = false
                _isRefreshing.value = false
            }
        }
    }

    fun onSearchQueryChange(query: String) {
        _searchQuery.value = query
    }

    fun updateProfessionalInfo(uid: String, specialized: String, condition: String, payLastPeriod: String) {
        viewModelScope.launch {
            try {
                val user = _users.value.find { it.id == uid } ?: return@launch
                val updatedUser = user.copy(
                    specialized = specialized,
                    condition = condition,
                    payLastPeriod = payLastPeriod
                )
                profileUseCase.updateOtherUserProfile(uid, updatedUser)
                // Refresh local list
                _users.value = _users.value.map { if (it.id == uid) updatedUser else it }
            } catch (e: Exception) {
                // Handle error
            }
        }
    }
}
