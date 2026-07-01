package com.example.testandroid.features.dashboard.presentation

import android.os.Build
import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.testandroid.cores.helpers.ConnectivityObserver
import com.example.testandroid.cores.managers.PermissionManager
import com.example.testandroid.cores.models.AppPermission
import com.example.testandroid.cores.models.UiState
import com.example.testandroid.features.auth.login.domain.AuthModel
import com.google.firebase.messaging.FirebaseMessaging
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    connectivityObserver: ConnectivityObserver,
    private val permissionManager: PermissionManager
) : ViewModel() {
    private val _uiState = MutableStateFlow<UiState<AuthModel>>(UiState.Idle)
    val uiState: StateFlow<UiState<AuthModel>> = _uiState.asStateFlow()

    val isConnected = connectivityObserver.isConnected

    fun onTriggerNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            permissionManager.request(
                permission = AppPermission.Notifications,
            )
        } else {
            fetchAndSendFcmToken()
        }
    }

    private fun fetchAndSendFcmToken() {
        FirebaseMessaging.getInstance().token
            .addOnSuccessListener { token ->
                Log.e("FCM", "Successfully get token")
            }
            .addOnFailureListener { e ->
                Log.e("FCM", "Failed to get token", e)
            }
    }

}