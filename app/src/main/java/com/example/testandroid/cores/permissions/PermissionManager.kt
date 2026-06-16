package com.example.testandroid.cores.permissions

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import javax.inject.Inject
import javax.inject.Singleton


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

