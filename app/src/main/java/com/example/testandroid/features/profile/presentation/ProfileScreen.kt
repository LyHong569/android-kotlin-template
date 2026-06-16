package com.example.testandroid.features.profile.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun ProfileScreen() {
    Scaffold { innerPadding ->
        Column(Modifier.padding(innerPadding)) {
            Text("Profile Screen")

            Button(onClick = {}) {
                Text("Logout")
            }
        }
    }
}