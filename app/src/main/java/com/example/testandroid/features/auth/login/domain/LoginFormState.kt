package com.example.testandroid.features.auth.login.domain

import com.example.testandroid.cores.models.FieldState

data class LoginFormState(
    val username: FieldState<String> = FieldState(""),
    val password: FieldState<String> = FieldState(""),
) {
    val isFormValid: Boolean
        get() = username.isValid && password.isValid
}

sealed interface LoginFormEvent {
    data class UsernameChanged(val username: String) : LoginFormEvent
    data class PasswordChanged(val password: String) : LoginFormEvent

    data object Submit : LoginFormEvent
}

object AuthValidator {
    fun validateUsername(value: String): String? = when {
        value.isBlank() -> "Email cannot be empty"
        else -> null
    }

    fun validatePassword(value: String): String? = when {
        value.isBlank() -> "Password cannot be empty"
        value.length < 6 -> "Password must be at least 6 characters"
        else -> null
    }
}