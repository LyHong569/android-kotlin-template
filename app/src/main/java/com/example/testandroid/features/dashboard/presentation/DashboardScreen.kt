package com.example.testandroid.features.dashboard.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.example.testandroid.R
import com.example.testandroid.features.profile.presentation.component.theme.ProfileLocaleBottomSheet

@Composable
fun DashboardScreen(viewModel: DashboardViewModel = hiltViewModel()) {
    Scaffold { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            Text(text = stringResource(R.string.hello_world))
            ProfileLocaleBottomSheet()
        }
    }
}