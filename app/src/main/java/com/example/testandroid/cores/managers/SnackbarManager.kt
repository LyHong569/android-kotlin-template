package com.example.testandroid.cores.managers

import androidx.compose.material3.SnackbarDuration
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

data class SnackbarMessage(
    val id: Long = System.currentTimeMillis(),
    val message: String,
    val duration: SnackbarDuration = SnackbarDuration.Short,
    val type: SnackbarType = SnackbarType.Default,
    val actionLabel: String? = null,
    val onAction: (() -> Unit)? = null
)

enum class SnackbarType { Default, Success, Error, Warning }


object SnackbarManager {
    private val _messages = MutableSharedFlow<SnackbarMessage>(
        extraBufferCapacity = 5
    )
    val messages = _messages.asSharedFlow()

    fun show(
        message: String,
        type: SnackbarType = SnackbarType.Default,
        actionLabel: String? = null,
        onAction: (() -> Unit)? = null
    ) {
        _messages.tryEmit(
            SnackbarMessage(
                message = message,
                type = type,
                actionLabel = actionLabel,
                onAction = onAction
            )
        )
    }
}