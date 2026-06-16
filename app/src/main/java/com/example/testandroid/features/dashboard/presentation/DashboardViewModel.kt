package com.example.testandroid.features.dashboard.presentation

import androidx.lifecycle.ViewModel
import com.example.testandroid.cores.helpers.ConnectivityObserver
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    connectivityObserver: ConnectivityObserver,
) :
    ViewModel() {
    val isConnected = connectivityObserver.isConnected
}