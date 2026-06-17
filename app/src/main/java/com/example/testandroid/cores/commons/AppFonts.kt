package com.example.testandroid.cores.commons

import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.intl.Locale
import com.example.testandroid.R


object AppFonts {
    val Default = FontFamily(
        Font(R.font.roboto_regular, FontWeight.Normal),
        Font(R.font.roboto_bold, FontWeight.Bold)
    )
    val Khmer = FontFamily(
        Font(R.font.kantumruy_pro_regular, FontWeight.Normal),
        Font(R.font.kantumruy_pro_bold, FontWeight.Bold)
    )

    fun forLocale(locale: Locale): FontFamily = when (locale.language) {
        "km" -> Khmer
        else -> Default
    }
}