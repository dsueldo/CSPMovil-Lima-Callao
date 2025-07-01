package com.colegiosociologosperu.cspmovillimacallao.domain.repositories

import com.colegiosociologosperu.cspmovillimacallao.domain.entities.user.User
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    val currentUser: Flow<User?>
    val currentUserId: String
    fun hasUser(): Boolean
    suspend fun signIn(email: String, password: String)
    suspend fun signUp(email: String, password: String)
    suspend fun sendEmailVerification()
    suspend fun sendPasswordResetEmail(email: String)
    suspend fun signOut()
    suspend fun deleteAccount()
    suspend fun getIdToken(): String?
    suspend fun refreshToken(): String?
    fun saveToken(token: String)
    fun getToken(): String?
    fun deleteToken()
}