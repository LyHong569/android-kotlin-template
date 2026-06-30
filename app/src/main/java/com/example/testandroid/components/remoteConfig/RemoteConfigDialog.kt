package com.example.testandroid.components.remoteConfig

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.testandroid.components.CustomDialog

@Composable
fun RemoteConfigDialog(viewModel: RemoteConfigDialogViewModel = hiltViewModel()) {
    val status by viewModel.remoteConfigStatus.collectAsStateWithLifecycle()

    when (status) {
        is RemoteConfigStatus.Maintenance -> {
            CustomDialog(
                title = "System Maintenance",
                description = "The application is currently under maintenance. Please try again later.",
                hasDismissButton = false,
                onDismiss = {},
                onConfirm = {}
            )
        }

        is RemoteConfigStatus.ForceUpdate -> {
            val minimumVersion = (status as RemoteConfigStatus.ForceUpdate).minimumVersion
            val url = (status as RemoteConfigStatus.ForceUpdate).url
            CustomDialog(
                title = "Update Required",
                description = "You must update to version $minimumVersion before continuing.",
                hasDismissButton = false,
                onDismiss = viewModel::onDismiss,
                onConfirm = { viewModel.onVersionConfirm(url) },
                confirmText = "Update Now"
            )
        }

        is RemoteConfigStatus.VersionOutDated -> {
            val minimumVersion = (status as RemoteConfigStatus.VersionOutDated).minimumVersion
            val url = (status as RemoteConfigStatus.VersionOutDated).url
            CustomDialog(
                title = "Update Available",
                description = "A newer version ($minimumVersion) is available. We recommend updating for the best experience.",
                confirmText = "Update",
                dismissText = "Later",
                onConfirm = { viewModel.onVersionConfirm(url) },
                onDismiss = viewModel::onDismiss,
            )
        }

        else -> {}
    }
}
