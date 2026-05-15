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
        return document.toObject(ProfileUiState::class.java)?.copy(id = document.id) ?: ProfileUiState(id = userId)
    }

    override suspend fun saveProfile(profile: ProfileUiState) {
        val uid = auth.currentUser?.uid ?: throw IllegalStateException("Usuario no autenticado")
        firestore.collection("profiles").document(uid).set(profile).await()
    }

    override suspend fun getAllUsers(): List<ProfileUiState> {
        val documents = firestore.collection("profiles").get().await()
        return documents.map { doc ->
            doc.toObject(ProfileUiState::class.java).copy(id = doc.id)
        }
    }

    override suspend fun updateOtherUserProfile(uid: String, profile: ProfileUiState) {
        firestore.collection("profiles").document(uid).set(profile).await()
    }
}
