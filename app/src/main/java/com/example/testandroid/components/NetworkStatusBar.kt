package com.example.testandroid.components

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun NetworkStatusBar(isConnected: Boolean) {
    var wasDisconnected by remember { mutableStateOf(false) }

    val height by animateDpAsState(
        targetValue = if (!isConnected) 24.dp else 0.dp,
        animationSpec = if (!isConnected) {
            tween(durationMillis = 500)
        } else {
            tween(durationMillis = 500, delayMillis = 4000)
        },
        label = "networkStatusBarHeight"
    )

    LaunchedEffect(isConnected) {
        if (!isConnected) wasDisconnected = true
    }

    val backgroundColor = if (isConnected) Color(0xFF4CAF50) else Color(0xFFF44336)
    val message = if (isConnected) "Back online" else "No connection"

    if (wasDisconnected || !isConnected) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .navigationBarsPadding()
                .background(backgroundColor)
                .height(height),
            contentAlignment = Alignment.Center
        ) {
            if (height > 0.dp) {
                Text(
                    text = message,
                    fontSize = 12.sp,
                    color = Color.White,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}