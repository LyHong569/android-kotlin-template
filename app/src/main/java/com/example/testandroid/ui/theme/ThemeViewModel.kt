package com.example.testandroid.ui.theme

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testandroid.cores.datastores.AppThemeManager
import com.example.testandroid.cores.models.ThemeMode
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ThemeViewModel @Inject constructor(
    private val appThemeManager: AppThemeManager
) : ViewModel() {

    val themeFlow: StateFlow<ThemeMode> =
        appThemeManager.themeFlow.stateIn(viewModelScope, SharingStarted.Eagerly, ThemeMode.SYSTEM)

    fun setThemeMode(mode: ThemeMode) {
        viewModelScope.launch {
            appThemeManager.changeTheme(mode)
        }
    }
}