package com.example.testandroid.cores.models

data class Language(
    val code: String,
    val displayLanguage: String
)

val AppLanguages = listOf(
    Language("en", "English"),
    Language("km", "Khmer"),
)