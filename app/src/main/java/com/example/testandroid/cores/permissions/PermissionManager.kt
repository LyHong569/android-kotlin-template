package com.example.testandroid.cores.permissions

import android.Manifest
import android.os.Build
import androidx.annotation.RequiresApi
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import javax.inject.Inject
import javax.inject.Singleton


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

@Singleton
class PermissionManager @Inject constructor() {
    private val _permissionRequest = MutableSharedFlow<PermissionRequest>(
        extraBufferCapacity = 1,
        replay = 1
    )
    val permissionRequest = _permissionRequest.asSharedFlow()

    fun request(
        permission: AppPermission,
        onGranted: () -> Unit = {},
        onDenied: () -> Unit = {},
        onPermanentlyDenied: () -> Unit = {},
    ) {
        if (!permission.isAvailable) {
            onGranted()
            return
        }
        _permissionRequest.tryEmit(
            PermissionRequest(
                permission = permission,
                onGranted = onGranted,
                onDenied = onDenied,
                onPermanentlyDenied = onPermanentlyDenied,
            )
        )
    }
}

