package com.colegiosociologosperu.cspmovillimacallao.domain.repositories

import android.net.Uri
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.colegiosociologosperu.cspmovillimacallao.domain.entities.user.ProfileUiState
import kotlinx.coroutines.tasks.await

object ProfileService {

    // Instancia de Firestore para interactuar con la base de datos
    private val db = Firebase.firestore

    // Instancia de Cloud Storage para almacenar archivos (imágenes)
    private val storage = Firebase.storage

    // Instancia de FirebaseAuth para obtener el UID del usuario autenticado
    private val auth = FirebaseAuth.getInstance()

    // Función para obtener el perfil del usuario desde Firestore
    suspend fun getProfile(): ProfileUiState? {
        // Obtiene el UID del usuario autenticado
        val uid = auth.currentUser?.uid ?: return null

        return try {
            // Consulta el documento del usuario en la colección "users" usando el UID
            val document = db.collection("profiles").document(uid).get().await()

            // Convierte el documento a un objeto Profile
            document.toObject(ProfileUiState::class.java)
        } catch (e: Exception) {
            // Maneja cualquier error que ocurra durante la consulta
            e.printStackTrace()
            null
        }
    }

    // Función para guardar o actualizar el perfil del usuario en Firestore
    suspend fun saveProfile(profile: ProfileUiState?) {
        // Obtiene el UID del usuario autenticado
        val uid = auth.currentUser?.uid ?: return

        try {
            // Guarda el objeto Profile en el documento del usuario en la colección "users"
            if (profile != null) {
                db.collection("profiles").document(uid).set(profile).await()
            }
        } catch (e: Exception) {
            // Maneja cualquier error que ocurra durante la operación de guardado
            e.printStackTrace()
        }
    }

    // Función para guardar la imagen de perfil del usuario en Cloud Storage
    suspend fun saveProfilePicture(uri: Uri) {
        // Obtiene el UID del usuario autenticado
        val uid = auth.currentUser?.uid ?: return

        try {
            // Crea una referencia al archivo en Cloud Storage con el UID del usuario
            val ref = storage.reference.child("profile_pictures/$uid.jpg")

            // Carga la imagen en Cloud Storage
            ref.putFile(uri).await()

            // Obtiene la URL de descarga de la imagen
            val downloadUrl = ref.downloadUrl.await()

            // Actualiza el perfil del usuario en Firestore con la URL de la imagen
            db.collection("profiles").document(uid)
                .update("profilePictureUrl", downloadUrl.toString()).await()
        } catch (e: Exception) {
            // Maneja cualquier error que ocurra durante la carga o actualización
            e.printStackTrace()
        }
    }
}