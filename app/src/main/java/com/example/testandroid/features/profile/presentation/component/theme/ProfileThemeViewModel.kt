package com.example.testandroid.features.profile.presentation.component.theme

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testandroid.cores.datastores.AppThemeManager
import com.example.testandroid.cores.datastores.ThemeMode
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

data class ThemeState(
    val selectedTheme: ThemeMode = ThemeMode.SYSTEM
)

@HiltViewModel
class ProfileThemeViewModel @Inject constructor(
    private val appThemeManager: AppThemeManager
) : ViewModel() {

    val themeState: StateFlow<ThemeState> = appThemeManager.themeFlow
        .map { ThemeState(selectedTheme = it) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = ThemeState()
        )

    fun setTheme(mode: ThemeMode) {
        viewModelScope.launch {
            appThemeManager.changeTheme(mode)
        }
    }

}