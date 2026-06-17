package com.example.testandroid.features.profile.presentation.component.locale

import androidx.lifecycle.ViewModel
import com.example.testandroid.cores.locales.AppLocaleManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

data class LocaleState(
    val selectedLanguage: String = ""
)

@HiltViewModel
class ProfileLocaleViewModel @Inject constructor(
    private val appLocaleManager: AppLocaleManager,
) : ViewModel() {

    private val _localeState = MutableStateFlow(LocaleState())
    val localeState: StateFlow<LocaleState> = _localeState

    init {
        loadInitialLanguage()
    }

    private fun loadInitialLanguage() {
        val currentLanguage = appLocaleManager.getLanguageCode()
        _localeState.value = _localeState.value.copy(selectedLanguage = currentLanguage)
    }

    fun changeLanguage(languageCode: String) {
        appLocaleManager.changeLanguage(languageCode)
        _localeState.value = _localeState.value.copy(selectedLanguage = languageCode)
    }

}

