package com.colegiosociologosperu.cspmovillimacallao.data.cache

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.security.crypto.MasterKey
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.tasks.await

class TokenManager(context: Context) {

    private val masterKeys = MasterKey.Builder(context)
        .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
        .build()

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences(
            "user_session",
            Context.MODE_PRIVATE
        )

    private val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()

    private val _currentUser = MutableStateFlow<FirebaseUser?>(firebaseAuth.currentUser)
    val currentUser: StateFlow<FirebaseUser?> = _currentUser.asStateFlow()

    init {
        firebaseAuth.addAuthStateListener { auth ->
            _currentUser.value = auth.currentUser
        }
    }

    fun saveUserSession(userId: String, token: String, expirationTime: Long) {
        sharedPreferences.edit().apply {
            putString("user_id", userId)
            putString("token", token)
            putLong("expiration_time", expirationTime)
            apply()
        }
    }

    fun updateToken(token: String, expirationTime: Long) {
        sharedPreferences.edit().apply {
            putString("token", token)
            putLong("expiration_time", expirationTime)
            apply()
        }
    }

    fun getUserSession(): UserSession? {
        val userId = sharedPreferences.getString("user_id", null)
        val token = sharedPreferences.getString("token", null)
        val expirationTime = sharedPreferences.getLong("expiration_time", 0)
        return if (userId != null && token != null && System.currentTimeMillis() < expirationTime) {
            UserSession(userId, token, expirationTime)
        } else {
            null
        }
    }

    fun clearUserSession() {
        sharedPreferences.edit().clear().apply()
        Log.d("SessionManager", "User session cleared: ${clearUserSession()} ")
    }

    suspend fun refreshTokenIfNeeded(): Boolean {
        val userSession = getUserSession() ?: return false
        val currentTime = System.currentTimeMillis()
        if (currentTime >= userSession.expirationTime - TOKEN_REFRESH_THRESHOLD) {
            return try {
                val newToken = firebaseAuth.currentUser?.getIdToken(true)?.await()?.token
                if (newToken != null) {
                    val newExpirationTime = currentTime + TOKEN_EXPIRATION_DURATION
                    updateToken(newToken, newExpirationTime)
                    true
                } else {
                    false
                }
            } catch (e: Exception) {
                Log.e("SessionManager", "Error refreshing token", e)
                false
            }
        }
        return true
    }

    companion object {
        private const val TOKEN_EXPIRATION_DURATION = 3600000 // 1 hour in milliseconds
        private const val TOKEN_REFRESH_THRESHOLD = 600000 // 10 minutes in milliseconds
    }
}

data class UserSession(
    val userId: String,
    val token: String,
    val expirationTime: Long,
)