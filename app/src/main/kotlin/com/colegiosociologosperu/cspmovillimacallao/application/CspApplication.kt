package com.colegiosociologosperu.cspmovillimacallao.application

import android.app.Application
import android.util.Log
import com.google.firebase.FirebaseApp
import com.google.firebase.appcheck.FirebaseAppCheck
// Elimina esta línea si la tienes: import com.google.firebase.appcheck.debug.BuildConfig
import com.google.firebase.appcheck.debug.DebugAppCheckProviderFactory
import com.google.firebase.appcheck.playintegrity.PlayIntegrityAppCheckProviderFactory
import dagger.hilt.android.HiltAndroidApp // Asumo que usas Hilt, esto es correcto
import com.colegiosociologosperu.cspmovillimacallao.BuildConfig

@HiltAndroidApp
class CspApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        // 1. Inicializa Firebase App Check
        FirebaseApp.initializeApp(this)

        val firebaseAppCheck = FirebaseAppCheck.getInstance()

        // 2. Instala el proveedor de atestación
        // Usa PlayIntegrityAppCheckProviderFactory para builds de release (producción)
        // Usa DebugAppCheckProviderFactory para builds de debug (desarrollo)
        if (BuildConfig.DEBUG) {
            // Para depuración: usa el proveedor de depuración.
            // Necesitarás añadir el token de depuración a Firebase Console.
            firebaseAppCheck.installAppCheckProviderFactory(
                DebugAppCheckProviderFactory.getInstance() // <--- ¡Asegúrate de que NO tenga argumentos!
            )
            Log.d("AppCheckDebug", "DebugAppCheckProviderFactory está siendo instalado.")
        } else {
            // Para release: usa el proveedor de Play Integrity.
            // Requiere que tu app esté distribuida en Google Play.
            firebaseAppCheck.installAppCheckProviderFactory(
                PlayIntegrityAppCheckProviderFactory.getInstance()
            )
            Log.d("AppCheckProd", "ProdAppCheckProviderFactory está siendo instalado.")
        }
    }
}