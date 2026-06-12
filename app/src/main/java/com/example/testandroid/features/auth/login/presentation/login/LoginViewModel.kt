package com.example.testandroid.features.auth.login.presentation.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testandroid.cores.datastores.SessionManager
import com.example.testandroid.cores.helpers.UiState
import com.example.testandroid.features.auth.login.domain.AuthModel
import com.example.testandroid.features.auth.login.domain.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class LoginState(
    val username: String = "",
    val password: String = "",
)

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val sessionManager: SessionManager
) : ViewModel() {
    private val _uiState = MutableStateFlow<UiState<AuthModel>>(UiState.Idle)
    val uiState: StateFlow<UiState<AuthModel>> = _uiState.asStateFlow()

    private val _formState = MutableStateFlow(LoginState())
    val formState = _formState.asStateFlow()

    fun onUsernameUpdate(value: String) = _formState.update { it.copy(username = value) }
    fun onPasswordUpdate(value: String) = _formState.update { it.copy(password = value) }


    fun login() {
        viewModelScope.launch {
            val (username, password) = _formState.value
            viewModelScope.launch {
                _uiState.value = UiState.Loading
                _uiState.value = try {
                    val result = authRepository.authLogin(username, password)
                    sessionManager.login(result.token)
                    UiState.Success(result)
                } catch (e: Exception) {
                    UiState.Error(e.message ?: "Unknown error")
                }
            }
        }
    }
}