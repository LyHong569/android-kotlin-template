package com.example.testandroid.cores.navigations

import androidx.compose.runtime.Composable
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay
import com.example.testandroid.features.auth.login.presentation.login.LoginScreen
import com.example.testandroid.features.dashboard.presentation.DashboardScreen
import com.example.testandroid.features.profile.presentation.ProfileScreen

@Composable
fun AppNavigation(navigator: Navigator) {
    if (navigator.backStack.isEmpty()) return

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