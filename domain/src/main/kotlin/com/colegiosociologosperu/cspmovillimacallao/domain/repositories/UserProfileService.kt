package com.colegiosociologosperu.cspmovillimacallao.domain.repositories

import com.colegiosociologosperu.cspmovillimacallao.domain.entities.user.ProfileUiState

interface UserProfileService {
    suspend fun getProfile(): ProfileUiState
    suspend fun saveProfile(profile: ProfileUiState)
}