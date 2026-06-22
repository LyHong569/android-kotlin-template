package com.example.testandroid.features.dashboard.presentation

import androidx.lifecycle.ViewModel
import com.example.testandroid.cores.helpers.ConnectivityObserver
import com.example.testandroid.cores.models.UiState
import com.example.testandroid.features.auth.login.domain.AuthModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val connectivityObserver: ConnectivityObserver
) : ViewModel() {
    private val _uiState = MutableStateFlow<UiState<AuthModel>>(UiState.Idle)
    val uiState: StateFlow<UiState<AuthModel>> = _uiState.asStateFlow()

    val isConnected = connectivityObserver.isConnected
    
}