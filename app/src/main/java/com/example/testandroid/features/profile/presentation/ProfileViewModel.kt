package com.example.testandroid.features.profile.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testandroid.cores.managers.GoogleAuthManager
import com.example.testandroid.cores.managers.SessionManager
import com.example.testandroid.cores.models.UiState
import com.example.testandroid.features.auth.login.domain.AuthModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val sessionManager: SessionManager,
    private val googleAuthManager: GoogleAuthManager
) :
    ViewModel() {

    private val _uiState = MutableStateFlow<UiState<AuthModel>>(UiState.Idle)
    val uiState: StateFlow<UiState<AuthModel>> = _uiState.asStateFlow()

    fun logout() {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            try {
                googleAuthManager.signOut()
                sessionManager.logout()
                UiState.Success(null)
            } catch (e: Exception) {
                UiState.Error(e.message ?: "Unknown error")
            }
        }

    }
}
