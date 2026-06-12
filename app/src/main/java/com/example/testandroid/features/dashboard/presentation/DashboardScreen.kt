package com.example.testandroid.features.dashboard.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.testandroid.components.SkeletonCard

@Composable
fun DashboardScreen(viewModel: DashboardViewModel = hiltViewModel()) {
    val isConnected by viewModel.isConnected.collectAsStateWithLifecycle()

    Scaffold { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
//            EmptyState(
//                "No Connection",
//                "Looks like you're offline. Please check your internet connection and try again soon",
//                isConnected = isConnected
//            )

            SkeletonCard()
        }
    }
}