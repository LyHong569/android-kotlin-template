package com.example.testandroid.cores.models

data class FieldState<T>(
    val value: T,
    val errorMessage: String? = null,
    val isTouched: Boolean = false
) {
    val isValid: Boolean get() = errorMessage == null
    val hasError: Boolean get() = isTouched && !isValid
}