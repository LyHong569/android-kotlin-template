package com.example.testandroid.features.auth.login.presentation.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testandroid.cores.managers.SessionManager
import com.example.testandroid.cores.models.UiState
import com.example.testandroid.features.auth.login.domain.AuthModel
import com.example.testandroid.features.auth.login.domain.AuthRepository
import com.example.testandroid.features.auth.login.domain.AuthValidator
import com.example.testandroid.features.auth.login.domain.LoginFormEvent
import com.example.testandroid.features.auth.login.domain.LoginFormState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val sessionManager: SessionManager
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState<AuthModel>>(UiState.Idle)
    val uiState: StateFlow<UiState<AuthModel>> = _uiState.asStateFlow()

    private val _formState = MutableStateFlow(LoginFormState())
    val formState = _formState.asStateFlow()

    fun onEvent(event: LoginFormEvent) {
        when (event) {
            is LoginFormEvent.UsernameChanged -> {
                _formState.update { current ->
                    current.copy(
                        username = current.username.copy(
                            value = event.username,
                            errorMessage = AuthValidator.validateUsername(event.username),
                            isTouched = true
                        )
                    )
                }
            }

            is LoginFormEvent.PasswordChanged -> {
                _formState.update { current ->
                    current.copy(
                        password = current.password.copy(
                            value = event.password,
                            errorMessage = AuthValidator.validatePassword(event.password),
                            isTouched = true
                        )
                    )
                }
            }

            is LoginFormEvent.Submit -> {
                _formState.update { current ->
                    current.copy(
                        username = current.username.copy(
                            errorMessage = AuthValidator.validateUsername(current.username.value),
                            isTouched = true
                        ),
                        password = current.password.copy(
                            errorMessage = AuthValidator.validatePassword(current.password.value),
                            isTouched = true
                        )
                    )
                }

                if (_formState.value.isFormValid) {
                    login()
                }
            }
        }
    }

    private fun login() {
        viewModelScope.launch {
            _uiState.value = UiState.Loading

            val username = _formState.value.username.value
            val password = _formState.value.password.value

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