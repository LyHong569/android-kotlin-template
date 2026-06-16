package com.example.testandroid.features.dashboard.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel

@Composable
fun DashboardScreen(viewModel: DashboardViewModel = hiltViewModel()) {
    Scaffold { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {

        }
    }
}