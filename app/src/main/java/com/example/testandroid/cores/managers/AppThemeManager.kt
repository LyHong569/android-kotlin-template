package com.example.testandroid.cores.managers

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.example.testandroid.cores.models.ThemeMode
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class AppThemeManager @Inject constructor(private val dataStore: DataStore<Preferences>) {
    private object PreferencesKeys {
        val THEME_MODE = stringPreferencesKey("theme_mode")
    }

    val themeFlow: Flow<ThemeMode> =
        dataStore.data.map { preferences ->
            val savedMode = preferences[PreferencesKeys.THEME_MODE]

            try {
                ThemeMode.valueOf(savedMode ?: ThemeMode.SYSTEM.name)
            } catch (e: IllegalArgumentException) {
                ThemeMode.SYSTEM
            }
        }

    suspend fun changeTheme(mode: ThemeMode) {
        dataStore.edit { preferences -> preferences[PreferencesKeys.THEME_MODE] = mode.name }
    }
}