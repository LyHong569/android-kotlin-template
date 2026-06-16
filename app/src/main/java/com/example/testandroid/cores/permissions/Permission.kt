package com.example.testandroid.cores.permissions

import android.Manifest
import android.os.Build
import androidx.annotation.RequiresApi

enum class AppPermission(val manifestPermission: String) {
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    Notifications(Manifest.permission.POST_NOTIFICATIONS),
    Camera(Manifest.permission.CAMERA);

    val isAvailable: Boolean
        get() = manifestPermission.isNotEmpty()
}


data class PermissionRequest(
    val permission: AppPermission,
    val onGranted: () -> Unit,
    val onDenied: () -> Unit,
    val onPermanentlyDenied: () -> Unit,
)