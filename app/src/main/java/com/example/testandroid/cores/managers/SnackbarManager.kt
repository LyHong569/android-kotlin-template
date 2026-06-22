package com.example.testandroid.cores.managers

import androidx.compose.material3.SnackbarDuration
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

data class SnackbarMessage(
    val id: Long = System.currentTimeMillis(),
    val message: String,
    val actionLabel: String? = null,
    val duration: SnackbarDuration = SnackbarDuration.Short,
    val type: SnackbarType = SnackbarType.Default
)

enum class SnackbarType { Default, Success, Error, Warning }


object SnackbarManager {
    private val _messages = MutableSharedFlow<SnackbarMessage>(
        extraBufferCapacity = 5
    )
    val messages = _messages.asSharedFlow()

    private fun show(message: SnackbarMessage) {
        _messages.tryEmit(message)
    }

    fun info(message: String) = show(
        SnackbarMessage(message = message, type = SnackbarType.Default)
    )


    fun success(message: String) = show(
        SnackbarMessage(message = message, type = SnackbarType.Success)
    )

    fun error(message: String) = show(
        SnackbarMessage(message = message, type = SnackbarType.Error)
    )

    fun warning(message: String) = show(
        SnackbarMessage(message = message, type = SnackbarType.Warning)
    )
}