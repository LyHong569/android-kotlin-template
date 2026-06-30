package com.example.testandroid

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.testandroid.components.NetworkStatusBar
import com.example.testandroid.components.PermissionDialogHandler
import com.example.testandroid.components.remoteConfig.RemoteConfigDialog
import com.example.testandroid.cores.managers.PermissionManager
import com.example.testandroid.cores.navigations.AppNavigation
import com.example.testandroid.cores.navigations.Dashboard
import com.example.testandroid.cores.navigations.Login
import com.example.testandroid.cores.navigations.Navigator
import com.example.testandroid.features.auth.login.domain.AuthState
import com.example.testandroid.ui.theme.TestAndroidTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val mainViewModel: MainViewModel by viewModels()

    @Inject
    lateinit var permissionManager: PermissionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashscreen = installSplashScreen()

        super.onCreate(savedInstanceState)

        splashscreen.setKeepOnScreenCondition {
            mainViewModel.authState.value == AuthState.Loading
        }

        enableEdgeToEdge()
        setContent {
            val authState by mainViewModel.authState.collectAsStateWithLifecycle()
            val isConnected by mainViewModel.isConnect.collectAsStateWithLifecycle()

            if (authState == AuthState.Loading) return@setContent

            val navigator = key(authState) {
                remember {
                    val initialRoute =
                        if (authState == AuthState.Authenticated) Dashboard else Login
                    Navigator(initialRoute)
                }
            }

            LaunchedEffect(authState) {
                if (authState == AuthState.Unauthenticated) navigator.replaceAll(Login)
            }

            TestAndroidTheme {
                RemoteConfigDialog()
                Scaffold(
                    bottomBar = {
                        NetworkStatusBar(isConnected)
                    }
                ) { innerPadding ->
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                    ) {
                        AppNavigation(navigator = navigator)
                    }

                    PermissionDialogHandler(permissionManager)
                }

            }
        }
    }
}