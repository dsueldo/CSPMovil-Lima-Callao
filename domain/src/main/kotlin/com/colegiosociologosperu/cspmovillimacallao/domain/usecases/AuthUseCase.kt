package com.colegiosociologosperu.cspmovillimacallao.domain.usecases

import com.colegiosociologosperu.cspmovillimacallao.domain.repositories.AuthRepository
import javax.inject.Inject

class AuthUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    val currentUser = repository.currentUser

    fun hasUser(): Boolean = repository.hasUser()
    
    suspend fun signIn(email: String, password: String) = repository.signIn(email, password)
    
    suspend fun signUp(email: String, password: String) = repository.signUp(email, password)
    
    suspend fun signOut() = repository.signOut()
    
    suspend fun deleteAccount() = repository.deleteAccount()
    
    suspend fun sendPasswordResetEmail(email: String) = repository.sendPasswordResetEmail(email)
    
    suspend fun sendEmailVerification() = repository.sendEmailVerification()
}
