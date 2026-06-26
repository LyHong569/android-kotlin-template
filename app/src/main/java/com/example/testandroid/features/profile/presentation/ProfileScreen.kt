package com.example.testandroid.features.profile.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.testandroid.cores.models.UiState
import com.example.testandroid.cores.navigations.Login
import com.example.testandroid.cores.navigations.Navigator

@Composable
fun ProfileScreen(navigator: Navigator, viewModel: ProfileViewModel = hiltViewModel()) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(state) {
        if (state is UiState.Success) {
            navigator.replaceAll(Login)
        }
    }

    Scaffold { innerPadding ->
        Column(Modifier.padding(innerPadding)) {
            Text("Profile Screen")
            Button(onClick = { navigator.goBack() }) {
                Text("Back")
            }
            Button(onClick = { viewModel.logout() }) {
                Text("Logout")
            }
        }
    }
}