package com.example.testandroid.features.auth.login.presentation.login

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.testandroid.components.TextInputField
import com.example.testandroid.components.TextInputFieldProps
import com.example.testandroid.cores.helpers.UiState
import com.example.testandroid.cores.navigations.Dashboard
import com.example.testandroid.cores.navigations.Navigator

@Composable
fun LoginScreen(
    navigator: Navigator,
    viewModel: LoginViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val formState by viewModel.formState.collectAsStateWithLifecycle()

    LaunchedEffect(uiState) {
        if (uiState is UiState.Success) {
            navigator.replaceAll(Dashboard)
        }
    }

    Scaffold { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(horizontal = 20.dp)
        ) {
            TextInputField(
                value = formState.username,
                onValueChange = viewModel::onUsernameUpdate,
                props = TextInputFieldProps(label = "Identity")
            )

            Spacer(modifier = Modifier.height(16.dp))

            TextInputField(
                value = formState.password,
                onValueChange = viewModel::onPasswordUpdate,
                props = TextInputFieldProps(label = "Password")
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = viewModel::login,
                enabled = uiState !is UiState.Loading,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                shape = RoundedCornerShape(6.dp),
            ) {
                if (uiState is UiState.Loading) {
                    CircularProgressIndicator()
                } else {
                    Text(text = "Log In", fontWeight = FontWeight.Medium)
                }
            }

            if (uiState is UiState.Error) {
                Spacer(modifier = Modifier.height(12.dp))
                Text(text = (uiState as UiState.Error).message)
            }
        }
    }
}