package com.example.testandroid.cores.navigations

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

// Auth
@Serializable
data object Login : NavKey

// Profile
@Serializable
data object Profile : NavKey

// Dashboard
@Serializable
data object Dashboard : NavKey

//@Serializable data object ProductList                      : NavKey
//@Serializable data class  ProductDetail(val id: Int)       : NavKey