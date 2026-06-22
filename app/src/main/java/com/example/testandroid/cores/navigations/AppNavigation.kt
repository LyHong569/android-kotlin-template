package com.example.testandroid.cores.navigations

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay
import com.example.testandroid.cores.managers.SnackbarManager
import com.example.testandroid.features.auth.login.presentation.login.LoginScreen
import com.example.testandroid.features.dashboard.presentation.DashboardScreen
import com.example.testandroid.features.profile.presentation.ProfileScreen

@Composable
fun AppNavigation(navigator: Navigator) {
    if (navigator.backStack.isEmpty()) return

    AppLayout(
        {
            NavDisplay(
                backStack = navigator.backStack,
                onBack = { navigator.goBack() },
                entryProvider = entryProvider {
                    // Auth
                    entry<Login> { LoginScreen(navigator) }

                    // Profile
                    entry<Profile> { ProfileScreen() }

                    // Dashboard
                    entry<Dashboard> { DashboardScreen() }
                }
            )
        }
    )


}

@Composable
fun AppLayout(content: @Composable () -> Unit) {
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(Unit) {
        SnackbarManager.messages.collect { message ->
            snackbarHostState.showSnackbar(
                message = message.message,
                actionLabel = message.actionLabel,
                duration = message.duration,
            )
        }
    }

    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        },
    ) { contentPadding ->
        Column(Modifier.padding(contentPadding)) {
            content()
        }
    }
}