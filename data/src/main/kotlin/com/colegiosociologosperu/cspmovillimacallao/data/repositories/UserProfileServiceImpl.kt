package com.colegiosociologosperu.cspmovillimacallao.data.repositories

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.colegiosociologosperu.cspmovillimacallao.domain.entities.user.ProfileUiState
import com.colegiosociologosperu.cspmovillimacallao.domain.repositories.UserProfileService
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class UserProfileServiceImpl @Inject constructor(
    private val auth: FirebaseAuth,
    private val firestore: FirebaseFirestore
) : UserProfileService {

    override suspend fun getProfile(): ProfileUiState {
        val userId = auth.currentUser?.uid ?: throw IllegalStateException("Usuario no autenticado")
        val document = firestore.collection("profiles").document(userId).get().await()
        return document.toObject(ProfileUiState::class.java) ?: ProfileUiState()
    }

    override suspend fun saveProfile(profile: ProfileUiState) {
        val uid = auth.currentUser?.uid ?: throw IllegalStateException("Usuario no autenticado")
        firestore.collection("profiles").document(uid).set(profile).await()
    }
}