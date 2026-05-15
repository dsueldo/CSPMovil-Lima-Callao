package com.colegiosociologosperu.cspmovillimacallao.presentation.utils

import android.content.Intent
import android.net.Uri
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import com.colegiosociologosperu.cspmovillimacallao.presentation.utils.theme.Red_Dark
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import com.google.android.play.core.install.model.AppUpdateType
import com.google.android.play.core.install.model.UpdateAvailability

@Composable
fun CheckForUpdates(
    onDismiss: () -> Unit
) {
    val context = LocalContext.current
    var showUpdateDialog by remember { mutableStateOf(false) }
    val appUpdateManager = remember { AppUpdateManagerFactory.create(context) }

    LaunchedEffect(Unit) {
        val appUpdateInfoTask = appUpdateManager.appUpdateInfo
        appUpdateInfoTask.addOnSuccessListener { appUpdateInfo ->
            if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE
                && appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.IMMEDIATE)
            ) {
                showUpdateDialog = true
            } else {
                onDismiss()
            }
        }.addOnFailureListener {
            onDismiss()
        }
    }

    if (showUpdateDialog) {
        UpdateDialog(
            onUpdate = {
                val packageName = context.packageName
                try {
                    context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=$packageName")))
                } catch (e: Exception) {
                    context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=$packageName")))
                }
                showUpdateDialog = false
                onDismiss()
            },
            onDismiss = {
                showUpdateDialog = false
                onDismiss()
            }
        )
    } else {
        // If no update is available, we still need to call onDismiss to let the caller know we're done
        // However, we should be careful not to call it immediately before the task finishes.
        // For simplicity in this flow, if no update is found, we can just proceed.
    }
}

@Composable
fun UpdateDialog(
    onUpdate: () -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = { /* Force user to choose */ },
        title = { Text("Actualización disponible") },
        text = { Text("Hay una nueva versión de la aplicación disponible en Play Store. ¿Deseas actualizar ahora?") },
        confirmButton = {
            Button(
                onClick = onUpdate,
                colors = ButtonDefaults.buttonColors(containerColor = Red_Dark)
            ) {
                Text("Actualizar", color = Color.White)
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Ahora no", color = Red_Dark)
            }
        }
    )
}
