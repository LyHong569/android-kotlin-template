package com.example.testandroid.features.dashboard.presentation

import android.os.Build
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.testandroid.R
import com.example.testandroid.components.EmptyState
import com.example.testandroid.cores.models.UiState
import com.example.testandroid.cores.navigations.Navigator
import com.example.testandroid.cores.navigations.Profile

@Composable
fun DashboardScreen(navigator: Navigator, viewModel: DashboardViewModel = hiltViewModel()) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val isConnected by viewModel.isConnected.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            viewModel.onTriggerNotificationPermission()
        }
    }

    Column {
        when (uiState) {
            is UiState.Loading -> {}
            is UiState.Error -> {
                EmptyState(
                    title = "Something Went Wrong",
                    description = "Please try again",
                    isConnected = isConnected,
                    onRetry = {
                        // Re fetch
                    }
                )
            }

            else -> {
                Text(text = stringResource(R.string.hello_world))
                Button(onClick = { navigator.navigate(Profile) }) { Text("Profile") }
                Button(onClick = { throw RuntimeException("Test Crashlytics Setup") }) { Text("Crash Test") }
            }
        }
    }

}