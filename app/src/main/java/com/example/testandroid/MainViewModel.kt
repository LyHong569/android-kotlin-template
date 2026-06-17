package com.example.testandroid

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testandroid.cores.datastores.SessionManager
import com.example.testandroid.cores.helpers.ConnectivityObserver
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    sessionManager: SessionManager,
    connectivityObserver: ConnectivityObserver
) : ViewModel() {
    val authState = sessionManager.authState
    val isConnect = connectivityObserver.isConnected


    init {
        viewModelScope.launch {
            sessionManager.validateSession()
        }
    }

}