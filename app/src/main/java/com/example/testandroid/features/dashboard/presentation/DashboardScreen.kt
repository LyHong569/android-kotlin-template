package com.example.testandroid.features.dashboard.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.testandroid.R
import com.example.testandroid.components.EmptyState
import com.example.testandroid.cores.models.UiState

@Composable
fun DashboardScreen(viewModel: DashboardViewModel = hiltViewModel()) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val isConnected by viewModel.isConnected.collectAsStateWithLifecycle()
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
            }
        }
    }

}