package com.example.testandroid.cores.navigations

import androidx.compose.runtime.mutableStateListOf
import androidx.navigation3.runtime.NavKey

class Navigator {
    val backStack = mutableStateListOf<NavKey>(Login)

    fun navigate(route: NavKey) {
        backStack.add(route)
    }

    // for bottom nav — clear stack and go to root tab
    fun navigateTab(route: NavKey) {
        backStack.clear()
        backStack.add(route)
    }

    fun goBack() {
        backStack.removeLastOrNull()
    }

    fun goBackTo(route: NavKey) {
        while (backStack.lastOrNull() != route) {
            backStack.removeLastOrNull() ?: break
        }
    }

    fun replaceAll(route: NavKey) {
        backStack.clear()
        backStack.add(route)
    }
}