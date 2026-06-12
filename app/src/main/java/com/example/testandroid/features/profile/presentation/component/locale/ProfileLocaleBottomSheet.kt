package com.example.testandroid.features.profile.presentation.component.locale

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.testandroid.cores.locales.Language
import com.example.testandroid.cores.locales.appLanguages

@Composable
fun ProfileLocaleBottomSheet(
    localeViewModel: ProfileLocaleViewModel = hiltViewModel()
) {
    val settingState by localeViewModel.localeState.collectAsStateWithLifecycle()

    val onAppLanguageChanged: (String) -> Unit = { newLanguage ->
        localeViewModel.changeLanguage(newLanguage)
    }

    SettingContent(
        selectedLanguage = settingState.selectedLanguage,
        onAppLanguageChanged = onAppLanguageChanged
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingContent(
    selectedLanguage: String,
    onAppLanguageChanged: (String) -> Unit,
) {
    Scaffold { paddingValues ->
        Box(
            modifier = Modifier.padding(paddingValues)
        ) {
            LazyColumn {
                items(appLanguages) { language ->
                    LanguageRow(
                        language = language,
                        isSelected = language.code == selectedLanguage,
                        onClick = {
                            onAppLanguageChanged(language.code)
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun LanguageRow(
    language: Language,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            Text(text = language.displayLanguage)
            Text(text = language.code)
        }

        RadioButton(
            selected = isSelected,
            onClick = onClick
        )
    }
}