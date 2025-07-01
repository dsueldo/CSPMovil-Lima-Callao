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
        FirebaseApp.initializeApp(this)

        val firebaseAppCheck = FirebaseAppCheck.getInstance()

        // Este es el proveedor de producción. Está bien que esté aquí.
        firebaseAppCheck.installAppCheckProviderFactory(
            PlayIntegrityAppCheckProviderFactory.getInstance()
        )

        // ESTE ES EL BLOQUE CLAVE PARA EL DEBUG TOKEN
        // Asegúrate de que BuildConfig.DEBUG es el de tu módulo (normalmente auto-generado por Gradle)
        if (BuildConfig.DEBUG) {
            firebaseAppCheck.installAppCheckProviderFactory(
                DebugAppCheckProviderFactory.getInstance() // <--- ¡Asegúrate de que NO tenga argumentos!
            )
            // Opcional: añade un Log.d para confirmar que este bloque se está ejecutando
            Log.d("AppCheckDebug", "DebugAppCheckProviderFactory está siendo instalado.")
        }
    }
}