package com.colegiosociologosperu.cspmovillimacallao.domain.repositories

import com.colegiosociologosperu.cspmovillimacallao.domain.entities.user.ProfileUiState

interface UserProfileService {
    suspend fun getProfile(): ProfileUiState
    suspend fun saveProfile(profile: ProfileUiState)
    suspend fun getAllUsers(): List<ProfileUiState>
    suspend fun updateOtherUserProfile(uid: String, profile: ProfileUiState)
}
