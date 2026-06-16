package com.example.testandroid.components

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import com.example.testandroid.cores.permissions.AppPermission
import com.example.testandroid.cores.permissions.PermissionManager
import com.example.testandroid.cores.permissions.PermissionRequest
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale

private enum class PermissionDialogState {
    None,
    Rationale,
    Settings
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun PermissionDialogHandler(permissionManager: PermissionManager) {
    var permissionRequest by remember { mutableStateOf<PermissionRequest?>(null) }
    var dialogState by remember { mutableStateOf(PermissionDialogState.None) }
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        permissionManager.permissionRequest.collect { request ->
            permissionRequest = request
            dialogState = PermissionDialogState.None
        }
    }

    permissionRequest?.let { request ->
        var resultFromSystemDialog by remember { mutableStateOf<Boolean?>(null) }

        val permissionState = rememberPermissionState(
            permission = request.permission.manifestPermission
        ) { isGranted ->
            resultFromSystemDialog = isGranted
        }

        LaunchedEffect(resultFromSystemDialog) {
            resultFromSystemDialog?.let { isGranted ->
                if (isGranted) {
                    dialogState = PermissionDialogState.None
                } else {
                    if (permissionState.status.shouldShowRationale) {
                        request.onDenied()
                        permissionRequest = null
                    } else {
                        dialogState = PermissionDialogState.Settings
                    }
                }
                resultFromSystemDialog = null
            }
        }

        LaunchedEffect(request) {
            if (!permissionState.status.isGranted) {
                if (permissionState.status.shouldShowRationale) {
                    dialogState = PermissionDialogState.Rationale
                } else {
                    permissionState.launchPermissionRequest()
                }
            }
        }

        LaunchedEffect(permissionState.status.isGranted) {
            if (permissionState.status.isGranted) {
                request.onGranted()
                permissionRequest = null
                dialogState = PermissionDialogState.None
            }
        }

        when (dialogState) {
            PermissionDialogState.Rationale -> {
                PermissionRationaleDialog(
                    permission = request.permission,
                    onConfirm = {
                        dialogState = PermissionDialogState.None
                        permissionState.launchPermissionRequest()
                    },
                    onDismiss = {
                        dialogState = PermissionDialogState.None
                        request.onDenied()
                        permissionRequest = null
                    }
                )
            }

            PermissionDialogState.Settings -> {
                PermissionSettingsDialog(
                    permission = request.permission,
                    onConfirm = {
                        context.openAppSettings()
                    },
                    onDismiss = {
                        dialogState = PermissionDialogState.None
                        request.onDenied()
                        permissionRequest = null
                    }
                )
            }

            PermissionDialogState.None -> Unit
        }
    }
}

@Composable
private fun PermissionRationaleDialog(
    permission: AppPermission,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Permission Required") },
        text = { Text("${permission.name} permission is needed to continue.") },
        confirmButton = {
            TextButton(onClick = onConfirm) { Text("Allow") }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("Deny") }
        }
    )
}

@Composable
private fun PermissionSettingsDialog(
    permission: AppPermission,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Permission Required") },
        text = {
            Text(
                "${permission.name} permission was denied. " +
                        "Please enable it in Settings to use this feature."
            )
        },
        confirmButton = {
            TextButton(onClick = onConfirm) { Text("Open Settings") }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("Not Now") }
        }
    )
}

fun Context.openAppSettings() {
    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
        data = Uri.fromParts("package", packageName, null)
        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    }
    startActivity(intent)
}