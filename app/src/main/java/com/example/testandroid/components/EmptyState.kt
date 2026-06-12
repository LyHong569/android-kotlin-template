package com.example.testandroid.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

enum class EmptyStateType {
    WIFI, DATA
}

private const val NO_CONNECTION_TITLE = "No Connection"
private const val NO_CONNECTION_DESC =
    "Looks like you're offline. Please check your internet connection and try again soon."

@Composable
fun EmptyState(
    title: String,
    description: String,
    type: EmptyStateType = EmptyStateType.DATA,
    isConnected: Boolean = true,
) {
    val displayTitle = if (!isConnected) NO_CONNECTION_TITLE else title
    val displayDesc = if (!isConnected) NO_CONNECTION_DESC else description
    val displayIcon = if (!isConnected) EmptyStateType.WIFI else type

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // TODO: add image vector
        getEmptyStateIcon(displayIcon)

        Text(
            text = displayTitle,
            fontSize = 22.sp,
            fontWeight = FontWeight.SemiBold
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = displayDesc,
            fontSize = 16.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 20.dp)
        )
    }
}

private fun getEmptyStateIcon(type: EmptyStateType): Unit { // ImageVector
    return when (type) {
        EmptyStateType.WIFI -> Unit
        else -> Unit
    }
}