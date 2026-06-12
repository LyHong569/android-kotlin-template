package com.example.testandroid.features.auth.login.presentation.login

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.testandroid.cores.navigations.Navigator
import com.example.testandroid.cores.navigations.Profile

@Composable
fun LoginScreen(navigator: Navigator) {
    Scaffold { innerPadding ->
        Column(Modifier.padding(innerPadding)) {
            Text("Login Screen")

            Button(onClick = { navigator.replaceAll(Profile) }) { Text("Login") }
        }
    }

}